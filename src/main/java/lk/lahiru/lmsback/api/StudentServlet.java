package lk.lahiru.lmsback.api;

//import jakarta.json.*;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;
//import java.io.BufferedReader;
//import java.io.IOException;

//@WebServlet(name = "StudentServlet", value = "/StudentServlet")
//public class StudentServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
//            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
//            return;
//        }
////
////        BufferedReader reader = req.getReader();
////        StringBuilder sb = new StringBuilder();
////        reader.lines().forEach(line -> sb.append(line + "\n"));
////        System.out.println(sb.toString());
//
//
////        JsonReader jsonReader = Json.createReader(req.getReader());
////        JsonObject jsonObject = jsonReader.readObject();
////        System.out.println(jsonObject.getString("name"));
//
//
//        JsonReader jsonReader = Json.createReader(req.getReader());
//        JsonArray array = jsonReader.readArray();
//        for (int i = 0; i < array.size(); i++) {
//            JsonObject elm1 = array.getJsonObject(i);
//            System.out.println(elm1.getString("id"));
//            System.out.println(elm1.getString("name"));
//
//
////            resp.setContentType("application/json");
////            JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
////            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
////            objectBuilder.add("id", "C005");
////            objectBuilder.add("name", "Jehan");
////            JsonObject obj = objectBuilder.build();
////            jsonWriter.writeObject(obj);
//
//            resp.setContentType("application/json");
//            JsonWriter jsonWriter = Json.createWriter(resp.getWriter());
//            JsonArrayBuilder jsonAb = Json.createArrayBuilder();
//            jsonAb.add(10);
//            jsonAb.add(false);
//            jsonAb.add(Json.createArrayBuilder().add(10).add(20).build());
//            jsonAb.add(Json.createObjectBuilder().add("id","C001").add("name","kasun").build());
//            JsonArray jsonArray= jsonAb.build();
//            jsonWriter.writeArray(jsonArray);
//
//
//
//
//
//
//        }
//
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }
//}


import jakarta.json.*;
        import jakarta.json.bind.Jsonb;
        import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import lk.lahiru.lmsback.model.StudentDTO;
import lk.lahiru.lmsback.util.Customer;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
        import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.xml.bind.ValidationException;
import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.ArrayList;
        import java.util.List;

public class StudentServlet extends HttpServlet {

    public volatile DataSource pool;

    public StudentServlet() {
    }

    @Override
    public void init() throws ServletException {
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
            pool =(DataSource) ctx.lookup("java:comp/env/jdbc/pool4lms");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null ||
                !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        Jsonb jsonb = JsonbBuilder.create();
        try {
            StudentDTO student = jsonb.fromJson(req.getReader(), StudentDTO.class);

            if (student.getName() == null || !student.getName().matches("[A-Za-z ]+")){
                throw new ValidationException("Invalid student name");
            }else if (student.getNic() == null || !student.getNic().matches("\\d{9}[Vv]")){
                throw new ValidationException("Invalid student nic");
            }else if (student.getEmail() == null || !student.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
                throw new ValidationException("Invalid student email");
            }

            System.out.println(student);
        }catch (JsonbException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        }catch (ValidationException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }



//        List<Customer> customerList = new ArrayList<>();

//        JsonReader jsonReader = Json.createReader(req.getReader());
//        JsonArray jsonArray = jsonReader.readArray();
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JsonObject jsonObject = jsonArray.getJsonObject(i);
//            String id = jsonObject.getString("id");
//            String name = jsonObject.getString("name");
//            String address = jsonObject.getString("address");
//            customerList.add(new Customer(id, name, address));
//        }

//        Jsonb jsonb = JsonbBuilder.create();
//        List<Customer> customerList = jsonb.fromJson(req.getReader(),
//                new ArrayList<Customer>(){}.getClass().getGenericSuperclass());

//        customerList.forEach(System.out::println);
//
//        List<Customer> anotherCustomerList = new ArrayList<>();
//        anotherCustomerList.add(new Customer("C001", "Nuwan", "Galle"));
//        anotherCustomerList.add(new Customer("C002", "Nuwan", "Galle"));
//        anotherCustomerList.add(new Customer("C003", "Nuwan", "Galle"));
//        anotherCustomerList.add(new Customer("C004", "Nuwan", "Galle"));
//        anotherCustomerList.add(new Customer("C005", "Nuwan", "Galle"));
//
//        resp.setContentType("application/json");
//        jsonb.toJson(anotherCustomerList, resp.getWriter());
//
//    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}