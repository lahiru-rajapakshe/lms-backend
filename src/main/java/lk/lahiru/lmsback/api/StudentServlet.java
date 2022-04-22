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
import lk.lahiru.lmsback.util.Customer;


import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.ArrayList;
        import java.util.List;

public class StudentServlet extends HttpServlet {

    public StudentServlet() {
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

        Jsonb jsonb = JsonbBuilder.create();
        List<Customer> customerList = jsonb.fromJson(req.getReader(),
                new ArrayList<Customer>(){}.getClass().getGenericSuperclass());

        customerList.forEach(System.out::println);

        List<Customer> anotherCustomerList = new ArrayList<>();
        anotherCustomerList.add(new Customer("C001", "Nuwan", "Galle"));
        anotherCustomerList.add(new Customer("C002", "Nuwan", "Galle"));
        anotherCustomerList.add(new Customer("C003", "Nuwan", "Galle"));
        anotherCustomerList.add(new Customer("C004", "Nuwan", "Galle"));
        anotherCustomerList.add(new Customer("C005", "Nuwan", "Galle"));

        resp.setContentType("application/json");
        jsonb.toJson(anotherCustomerList, resp.getWriter());

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}