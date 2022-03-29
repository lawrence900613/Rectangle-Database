/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;



@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index(Map<String, Object> model) {
    String name = "Bobby";
    model.put("name", name);
    return "index";
  }

  /*
  @GetMapping(
    path = "/rectangle"
  )
  public String getRectangleForm(Map<String, Object> model){
    Rectangle rectangle = new Rectangle();  // creates new person object with empty fname and lname
    model.put("rectangle", rectangle);
    return "rectangle";
  }
*/

  @GetMapping("/rectangle")
  public String getRectangleForm(Map<String, Object> model){
    Rectangle rectangle = new Rectangle();  // creates new person object with empty fname and lname
    model.put("rectangle", rectangle);
    return "rectangle";
  }


  @PostMapping(
    path = "/rectangle",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleBrowserSubmit(Map<String, Object> model, Rectangle rectangle) throws Exception {
    // Save the person data into the database
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rectangle2 (id serial, name varchar(20), color varchar(20), height numeric, width numeric)");
      String sql ="INSERT INTO rectangle2(name,color,height,width) VALUES ('" + rectangle.getName() +"','"+ rectangle.getColor() +"',"+ rectangle.getHeight() +","+ rectangle.getWidth()+")";
      stmt.executeUpdate(sql);
      System.out.println(rectangle.getName() +"','"+ rectangle.getColor() +"',"+ rectangle.getHeight() +","+ rectangle.getWidth());

      return "redirect:/Database";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @GetMapping("/rectangle/success")
  public String getRectangleiSuccess(){
    return "Database";
  }

  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }


  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }

  
  @GetMapping("/Database")
  public String getDatabase(Map<String, Object> model){
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rectangle2 (id serial, name varchar(20), color varchar(20), height numeric, width numeric)");
      ResultSet rs = stmt.executeQuery("SELECT * FROM rectangle2");

      ArrayList<Rectangle> output = new ArrayList<Rectangle>();
      while (rs.next()) {
        Rectangle temp = new Rectangle();
        temp.setName(rs.getString("name"));
        temp.setColor(rs.getString("color"));
        temp.setHeight(rs.getFloat("height"));
        temp.setWidth(rs.getFloat("width"));
        temp.setID(rs.getInt("id"));
        output.add(temp);
      }

      model.put("records", output);
      return "Database";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }

  }
  @PostMapping(path="/RectangleInfo2/{id}",
  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String DeleteRectangle (@PathVariable String id){
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("DELETE FROM rectangle2 WHERE id=" + id +";");
      return "redirect:/Database";
    } catch (Exception e) {
      return "error";
    }
  }
 
  @GetMapping("/RectangleInfo/{pid}")
  public String getSpecificPerson(Map<String, Object> model, @PathVariable String pid){
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rectangle2 (id serial, name varchar(20), color varchar(20), height integer, width integer)");
      ResultSet rs = stmt.executeQuery("SELECT * FROM rectangle2");
      int number = Integer.parseInt(pid);
      while (rs.next()) {
        if(rs.getInt("id") == number){
          break;
        }
      }
      Rectangle temp = new Rectangle();
      temp.setName(rs.getString("name"));
      temp.setColor(rs.getString("color"));
      temp.setHeight(rs.getFloat("height"));
      temp.setWidth(rs.getFloat("width"));
      temp.setID(rs.getInt("id"));

      model.put("records", temp);
      return "RectangleInfo";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }

  }



  }


  







/*
<table id="myTable2" class ="myTable2">
  <tr>
    <th>Name</th>
    <th>Color</th>
    <th>Height</th>
    <th>Width</th>
  </tr>
  <tr>
    <td ><form action="#" method="post" th:action="@{/rectangle}" th:object="${rectangle}">
      <input type = "text" th:field=*{name}> 
    </form></td>
    <td><form action="#" method="post" th:action="@{/rectangle}" th:object="${rectangle}" >
      <input type = "text" th:field=*{color}> 
    </form></td>
    <td><form action="#" method="post" th:action="@{/rectangle}" th:object="${rectangle}" >
      <input type = "number" id = "AW1" min="0"> 
    </form></td>
    <td>
    <form action="#" method="post" th:action="@{/rectangle}" th:object="${rectangle}" >
      <input type = "number" id = "AG1" min="0"> 
    </form></td>
  </tr>
</table>*/