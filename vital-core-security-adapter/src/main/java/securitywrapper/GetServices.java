package securitywrapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import jsonpojos.Application;
import jsonpojos.ApplicationType;
import jsonpojos.ApplicationTypes;
import jsonpojos.Applications;
import jsonpojos.AttributeValue;
import jsonpojos.Group;
import jsonpojos.Groups;
import jsonpojos.Policies;
import jsonpojos.Policy;
import jsonpojos.Result;
import jsonpojos.SimpleDate;
import jsonpojos.User;
import jsonpojos.Users;
import jsonpojos.Validation;
import jsonpojos.Monitor;
import jsonpojos.Permissions;
import jsonpojos.PermissionsCollection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtils;
import utils.MD5Util;
import utils.UTF8fixer;
import clients.OpenAMClient;

@Path("/rest")
public class GetServices {
    private OpenAMClient client;
    
    public GetServices() {
        client = new OpenAMClient();
    }
    
    @Path("/user/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(
            @PathParam("id") String userId,
            @HeaderParam("Cookie") String cookie) {
        User user;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");

        answer = null;
        code = 0;
        user = client.getUser(UTF8fixer.convert(userId), token);
        
        try {
            answer = JsonUtils.serializeJson(user);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (user.getAdditionalProperties().containsKey("code")) {
            if (user.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) user.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/user/{id}/groups")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserGroups(
            @PathParam("id") String userId,
            @HeaderParam("Cookie") String cookie) {
        String answer;
        
        answer = null;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        Groups groups = client.listUserGroups(UTF8fixer.convert(userId), token);
        
        try {
            answer = JsonUtils.serializeJson(groups);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return Response.ok()
            .entity(answer)
            .build();
    }
    
    @Path("/group/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroup(
            @PathParam("id") String groupId,
            @HeaderParam("Cookie") String cookie)  {
        Group group;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");

        String converted;
        byte[] bytes = new byte[groupId.length()];

        for (int i = 0; i < groupId.length(); i++) {
            bytes[i] = (byte) groupId.charAt(i);
        }

        try {
            converted = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            converted = null;
        }
 
        answer = null;
        code = 0;
        group = client.getGroup(converted, token);
        
        try {
            answer = JsonUtils.serializeJson(group);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (group.getAdditionalProperties().containsKey("code")) {
            if (group.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) group.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/policy/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPolicy(
            @PathParam("id") String policyId,
            @HeaderParam("Cookie") String cookie) {
        Policy policy;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        policy = client.getPolicy(UTF8fixer.convert(policyId), token);
        
        try {
            answer = JsonUtils.serializeJson(policy);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (policy.getAdditionalProperties().containsKey("code")) {
            if (policy.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) policy.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/application/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplication(
            @PathParam("id") String applicationId,
            @HeaderParam("Cookie") String cookie) {
        Application application;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        application = client.getApplication(UTF8fixer.convert(applicationId), token);
        
        try {
            answer = JsonUtils.serializeJson(application);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (application.getAdditionalProperties().containsKey("code")) {
            if (application.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) application.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/apptype/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplicationType(
            @PathParam("id") String applicationTypeId,
            @HeaderParam("Cookie") String cookie) {
        ApplicationType applicationType;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        applicationType = client.getApplicationType(UTF8fixer.convert(applicationTypeId), token);
        
        try {
            answer = JsonUtils.serializeJson(applicationType);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (applicationType.getAdditionalProperties().containsKey("code")) {
            if (applicationType.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) applicationType.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/application/{id}/policies")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplicationPolicies(
            @PathParam("id") String appName,
            @HeaderParam("Cookie") String cookie) {
        String answer;
        
        answer = null;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        Policies policies = client.listApplicationPolicies(UTF8fixer.convert(appName), token);
        
        try {
            answer = JsonUtils.serializeJson(policies);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return Response.ok()
            .entity(answer)
            .build();
    }
    
    @Path("/users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(
            @HeaderParam("Cookie") String cookie) {
        Users users;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        users = client.getUsers(token);
        
        try {
            answer = JsonUtils.serializeJson(users);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (users.getAdditionalProperties().containsKey("code")) {
            if (users.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) users.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/groups")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroups(
            @HeaderParam("Cookie") String cookie) {
        Groups groups;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        groups = client.getGroups(token);
        
        try {
            answer = JsonUtils.serializeJson(groups);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (groups.getAdditionalProperties().containsKey("code")) {
            if (groups.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) groups.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/policies")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPolicies(
            @HeaderParam("Cookie") String cookie) {
        Policies policies;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        policies = client.getPolicies(token);
        
        try {
            answer = JsonUtils.serializeJson(policies);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (policies.getAdditionalProperties().containsKey("code")) {
            if (policies.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) policies.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/applications")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplications(
            @HeaderParam("Cookie") String cookie) {
        Applications apps;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        apps = client.getApplications(token);
        
        try {
            answer = JsonUtils.serializeJson(apps);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (apps.getAdditionalProperties().containsKey("code")) {
            if (apps.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) apps.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/apptypes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplicationTypes(
            @HeaderParam("Cookie") String cookie) {
        ApplicationTypes appTypes;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        appTypes = client.getApplicationTypes(token);
        
        try {
            answer = JsonUtils.serializeJson(appTypes);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (appTypes.getAdditionalProperties().containsKey("code")) {
            if (appTypes.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) appTypes.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/stats")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessions(
            @HeaderParam("Cookie") String cookie) {
        Monitor values;
        String answer;
        int code;
        
        String token = null;
        if (cookie != null)
            token = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        
        answer = null;
        code = 0;
        values = client.getStats(token);
        
        try {
            answer = JsonUtils.serializeJson(values);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (values.getAdditionalProperties().containsKey("code")) {
            if (values.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) values.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserFromToken(
            @QueryParam("testCookie") boolean altCookie,
            @HeaderParam("Cookie") String cookie) {
        Validation val;
        String token, answer;
        int code;
        
        answer = null;
        code = 0;
        
        String altToken = null, ssoToken = null;
        if (cookie != null) {
            altToken = cookie.replaceAll(".*altToken=([^;]*).*", "$1");
            ssoToken = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        }
        
        if (altCookie)
            token = altToken;
        else
            token = ssoToken;
        
        val = client.getUserIdFromToken(token);
        User user = client.getUser(val.getUid(), token); // get the info
        
        List<String> gn = null;
        gn = user.getGivenName();
        if ((gn != null) && (!gn.isEmpty())) { // send back the first name if available
            val.setName(gn.get(0));
        } else {
            gn = user.getGivenname();
            if ((gn != null) && (!gn.isEmpty())) {
                val.setName(gn.get(0));
            }
        }
        List<String> ln = null;
        ln = user.getSn();
        if ((gn != null) && (!gn.isEmpty()) && (ln != null) && (!ln.isEmpty())) { // send back the full name if available
            val.setFullname(gn.get(0) + " " + ln.get(0)); // composed by first name + last name
        } else { // otherwise use common name, but it is not the full name for sure
            List<String> cn = null;
            cn = user.getCn();
            if ((cn != null) && (!cn.isEmpty())) {
                val.setFullname(cn.get(0));
            }
        }
        
        List<String> mail = null;
        mail = user.getMail();
        if ((mail != null) && (!mail.isEmpty())) {
            val.setMailhash(MD5Util.md5Hex(mail.get(0)));
        }
        
        List<String> time = user.getCreateTimestamp();
        SimpleDate date = new SimpleDate();
        if ((time != null) && (!time.isEmpty())) {
            date.setYear(time.get(0).substring(0, 4));
            int m = Integer.parseInt(time.get(0).substring(4, 6));
            date.setMonth(new DateFormatSymbols().getMonths()[m - 1]);
            date.setDay(time.get(0).substring(6, 8));
            val.setCreation(date);
        }
        
        try {
            answer = JsonUtils.serializeJson(val);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (val.getAdditionalProperties().containsKey("code")) {
            if (val.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) val.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/validate")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateToken(
            @HeaderParam("Cookie") String cookie,
            @QueryParam("testCookie") boolean altCookie) {
        Validation val;
        String answer;
        int code;
        
        String altToken = null, ssoToken = null;
        if (cookie != null) {
            altToken = cookie.replaceAll(".*altToken=([^;]*).*", "$1");
            ssoToken = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        }
        
        answer = null;
        code = 0;
        if (altCookie) {
            val = client.validateToken(ssoToken, altToken);
        } else {
            val = client.validateToken(ssoToken, ssoToken);
        }
        
        try {
            answer = JsonUtils.serializeJson(val);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (val.getAdditionalProperties().containsKey("code")) {
            if (val.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) val.getAdditionalProperties().get("code");
            }
        }
        if (code >= 400 && code < 500) {
            return Response.status(Status.BAD_REQUEST)
                .entity(answer)
                .build();
        }
        else if (code >= 500 && code < 600) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(answer)
                .build();
        }
        else {
            return Response.ok()
                .entity(answer)
                .build();
        }
    }
    
    @Path("/getresource")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getresource(
            @HeaderParam("Cookie") String cookie,
            @QueryParam("testCookie") boolean altCookie,
            @QueryParam("resource") String resource) {
        Cookie ck;
        CloseableHttpClient httpclient;

        httpclient = HttpClients.createDefault();

        URI uri = null;
        try {
            uri = new URI(UTF8fixer.convert(resource));
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        String altToken = null, ssoToken = null;
        if (cookie != null) {
            altToken = cookie.replaceAll(".*altToken=([^;]*).*", "$1");
            ssoToken = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        }
        
        HttpGet httpget = new HttpGet(uri);
        if (altCookie) {
            ck = new Cookie(client.getSSOCookieName(), altToken);
        } else {
            ck = new Cookie(client.getSSOCookieName(), ssoToken);
        }
        
        httpget.setHeader("Cookie", ck.toString());
        httpget.setConfig(RequestConfig.custom().setConnectionRequestTimeout(3000).setConnectTimeout(3000).setSocketTimeout(3000).build());

        // Execute and get the response.
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                // Try again with a higher timeout
                try {
                    Thread.sleep(1000); // do not retry immediately
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                httpget.setConfig(RequestConfig.custom().setConnectionRequestTimeout(7000).setConnectTimeout(7000).setSocketTimeout(7000).build());
                response = httpclient.execute(httpget);
            } catch (ClientProtocolException ea) {
                ea.printStackTrace();
            } catch (IOException ea) {
                try {
                    // Try again with a higher timeout
                    try {
                        Thread.sleep(1000); // do not retry immediately
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    httpget.setConfig(RequestConfig.custom().setConnectionRequestTimeout(12000).setConnectTimeout(12000).setSocketTimeout(12000).build());
                    response = httpclient.execute(httpget);
                } catch (ClientProtocolException eaa) {
                    ea.printStackTrace();
                } catch (IOException eaa) {
                    ea.printStackTrace();
                    return Response.ok()
                        .entity(eaa.getMessage())
                        .build();
                }
            }
        }

        HttpEntity entity = response.getEntity();
        String respString = "";

        if (entity != null) {
            try {
                respString = EntityUtils.toString(entity);
                response.close();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }

        return Response.ok()
            .entity(respString)
            .build();
    }
    
    @Path("/permissions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPermissions(
            @HeaderParam("Cookie") String cookie,
            @DefaultValue("false") @QueryParam("testCookie") boolean altCookie) {
        // One token is used to identify the user, the other one is to authorize the requests
        String tokenPerformer, tokenUser;
        boolean error = false;
        String errorMsg = "";
        int code;

        //System.out.println(cookie);

        String altToken = null, ssoToken = null;
        if (cookie != null) {
            altToken = cookie.replaceAll(".*altToken=([^;]*).*", "$1");
            ssoToken = cookie.replaceAll(".*ssoToken=([^;]*).*", "$1");
        }

        //System.out.println(ssoToken);
        //System.out.println(altToken);

        if (ssoToken == null || ssoToken.equals("") || altToken == null || altToken.equals("")) {
            return Response.status(Status.UNAUTHORIZED)
                .entity("{ \"code\": 401, \"reason\": \"Unauthorized\", \"message\": \"Missing or invalid user token!\"}")
                .build();
        }
        
        if (altCookie) {
            tokenPerformer = ssoToken;
            tokenUser = altToken;
        } else {
            tokenPerformer = altToken;
            tokenUser = ssoToken;
        }
        
        PermissionsCollection resp = new PermissionsCollection();
        
        Permissions permStore = new Permissions();
        Permissions permRetrieve = new Permissions();
        
        Map<String, List<String>> allowedStore = new HashMap<String, List<String>>();
        Map<String, List<String>> deniedStore = new HashMap<String, List<String>>();
        
        Map<String, List<String>> allowedRetrieve = new HashMap<String, List<String>>();
        Map<String, List<String>> deniedRetrieve = new HashMap<String, List<String>>();
        
        Validation val = client.getUserIdFromToken(tokenUser);
        Groups groups = client.listUserGroups(val.getUid(), tokenPerformer);
        Policies policies = client.getPolicies(tokenPerformer);
        
        if (val.getAdditionalProperties().containsKey("code")) {
            if (val.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) val.getAdditionalProperties().get("code");
                if (code < 200 || code > 299) {
                    error = true;
                    try {
                        errorMsg = JsonUtils.serializeJson(val);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (policies.getAdditionalProperties().containsKey("code")) {
            if (policies.getAdditionalProperties().get("code").getClass() == Integer.class) {
                code = (Integer) policies.getAdditionalProperties().get("code");
                if (code < 200 || code > 299) {
                    error = true;
                    try {
                        errorMsg = JsonUtils.serializeJson(policies);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        if (!error) {
            if (!val.getValid()) {
                return Response.status(Status.UNAUTHORIZED)
                    .entity("{ \"code\": 401, \"reason\": \"Unauthorized\", \"message\": \"Missing or invalid user token!\"}")
                    .build();
            }
            List<Result> list = policies.getResult();
            Iterator<Result> iter = list.listIterator();
            
            while (iter.hasNext()) {
                Result policy = iter.next();
                if (policy.getApplicationName().equals("Data access control") && policy.getSubject().getType().equals("Identity") && policy.getActive()) {
                    List<String> polgroups = policy.getSubject().getSubjectValues();
                    Iterator<String> iterint = polgroups.iterator();
                    while (iterint.hasNext()) {
                        String group = iterint.next();
                        if (groups.getResult().contains(group.substring(group.indexOf('=') + 1, group.indexOf(',')))) {
                            List<String> listCond = policy.getResources();
                            Iterator<String> iterCond = listCond.listIterator();
                            while (iterCond.hasNext()) {
                                String cond = iterCond.next();
                                int s = cond.indexOf('$');
                                if (s > 0) {
                                    String attribute = cond.substring(0, s).replaceAll(":80", "");
                                    String value = cond.substring(s + 1);
                                    AttributeValue av = new AttributeValue();
                                    av.setAttribute(attribute);
                                    av.setValue(value);
                                    if (policy.getActionValues().getRETRIEVE() != null) {
                                        if (policy.getActionValues().getRETRIEVE() == true) {
                                            if (!allowedRetrieve.containsKey(av.getAttribute())) {
                                                allowedRetrieve.put(av.getAttribute(), new ArrayList<String>());
                                            }
                                            if (!deniedRetrieve.containsKey(av.getAttribute()) || !deniedRetrieve.get(av.getAttribute()).contains(av.getValue())) {
                                                allowedRetrieve.get(av.getAttribute()).add(av.getValue());
                                            }
                                        }
                                        else if (policy.getActionValues().getRETRIEVE() == false) {
                                            if (!deniedRetrieve.containsKey(av.getAttribute())) {
                                                deniedRetrieve.put(av.getAttribute(), new ArrayList<String>());
                                            }
                                            deniedRetrieve.get(av.getAttribute()).add(av.getValue());
                                            if (allowedRetrieve.containsKey(av.getAttribute()))
                                                allowedRetrieve.get(av.getAttribute()).remove(av.getValue());
                                        }
                                    }
                                    if (policy.getActionValues().getSTORE() != null) {
                                        if (policy.getActionValues().getSTORE() == true) {
                                            if (!allowedStore.containsKey(av.getAttribute())) {
                                                allowedStore.put(av.getAttribute(), new ArrayList<String>());
                                            }
                                            if (!deniedStore.containsKey(av.getAttribute()) || !deniedStore.get(av.getAttribute()).contains(av.getValue())) {
                                                allowedStore.get(av.getAttribute()).add(av.getValue());
                                            }
                                        }
                                        else if (policy.getActionValues().getSTORE() == false) {
                                            if (!deniedStore.containsKey(av.getAttribute())) {
                                                deniedStore.put(av.getAttribute(), new ArrayList<String>());
                                            }
                                            deniedStore.get(av.getAttribute()).add(av.getValue());
                                            if (allowedStore.containsKey(av.getAttribute()))
                                                allowedStore.get(av.getAttribute()).remove(av.getValue());
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
            
            permStore.setAllowed(allowedStore);
            permStore.setDenied(deniedStore);
            
            permRetrieve.setAllowed(allowedRetrieve);
            permRetrieve.setDenied(deniedRetrieve);
            
            resp.setStore(permStore);
            resp.setRetrieve(permRetrieve);
            
            return Response.ok()
                .entity(resp)
                .build();
        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(errorMsg)
                .build();
        }
    }

    @Path("/unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnauthorized() {
        // Used by the Policy Agent on missing cookie or invalid token to return a proper error
        return Response.status(Status.UNAUTHORIZED)
            .entity("{ \"code\": 401, \"reason\": \"Unauthorized\", \"message\": \"Missing or invalid SSO token!\"}")
            .build();
    }

}

