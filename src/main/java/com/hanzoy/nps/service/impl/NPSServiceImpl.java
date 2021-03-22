package com.hanzoy.nps.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanzoy.nps.domain.Client;
import com.hanzoy.nps.dto.CommonResult;
import com.hanzoy.nps.dto.npsDto.Clients;
import com.hanzoy.nps.mapper.ClientMapper;
import com.hanzoy.nps.po.ClientPO;
import com.hanzoy.nps.service.NPSService;
import com.hanzoy.nps.service.UserService;
import com.hanzoy.nps.utils.ClassCopyUtils.ClassCopyUtils;
import com.hanzoy.nps.vo.ClientVO;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@ConfigurationProperties(prefix = "nps")
public class NPSServiceImpl implements NPSService {

    @Autowired
    UserService userService;

    @Resource
    ClientMapper clientMapper;

    private static String COOKIE_beegosessionID;
    private static long date;

    private String username;
    private String password;
    private String ip;
    private String port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    final OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    @Override
    public String getCOOKIE_beegosessionID(){
        if(date == 0 || System.currentTimeMillis() - date > 15*60*1000){
            try {
                Request request = new Request.Builder()
                        .url("http://"+ip+":"+port+"/login/index")
                        .build();
                Response response = null;
                response = client.newCall(request).execute();


                List<String> headers = response.headers("Set-Cookie");
                COOKIE_beegosessionID = headers.get(0).split(";", 2)[0];

                FormBody formBody = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build();
                request = new Request.Builder()
                        .url("http://"+ip+":"+port+"/login/verify")
                        .post(formBody)
                        .addHeader("Cookie", COOKIE_beegosessionID)
                        .build();
                client.newCall(request).execute();


                date = System.currentTimeMillis();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return COOKIE_beegosessionID;
    }

    public Clients getClientList(String search) {

        try{
            if(search == null){
                search = "";
            }
            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("search", search)
                    .add("order", "asc")
                    .add("offset", "0")
                    .add("limit", "10000")
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+ip+":"+port+"/client/list")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            String string = Objects.requireNonNull(response.body()).string();
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> map = objectMapper.readValue(string, HashMap.class);
            Clients clients = new Clients();
            clients.setIp(map.get("ip").toString());
            clients.setBridgePort(map.get("bridgePort").toString());
            clients.setBridgeType(map.get("bridgeType").toString());
            ArrayList<Object> rowsO = (ArrayList<Object>) map.get("rows");
            ArrayList<Clients.Client> rows = new ArrayList<>();
            for (Object o : rowsO) {
                HashMap<String, Object> os = (HashMap<String, Object>) o;
                Clients.Client client = new Clients.Client();
                client.setId((Integer) os.get("Id"));
                client.setVerifyKey(os.get("VerifyKey").toString());
                client.setAddr(os.get("Addr").toString());
                client.setRemark(os.get("Remark").toString());
                client.setStatus((Boolean) os.get("Status"));
                client.setIsConnect((Boolean) os.get("IsConnect"));
                rows.add(client);
            }
            clients.setRows(rows);
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CommonResult getClientList(String search, String token) {
        //检查token
        userService.checkToken(token);

        //调取网络接口获取clients
        Clients clients = getClientList("");

        //获取本地数据库内容
//        ArrayList<ClientPO> clientPOList = clientMapper.selectAllClient();
        ArrayList<ClientPO> clientPOList = clientMapper.selectClient(search);

        ClientVO clientVO = new ClientVO();
        ArrayList<ClientVO.Client> rows = new ArrayList<>();
/*
        //将数据拷贝到VO中
        for (Clients.Client Crow : clients.getRows()) {

            ClientVO.Client row = new ClientVO.Client();
            ClassCopyUtils.ClassCopy(row, Crow);
            //遍历寻找creator并添加到VO中
            for (ClientPO clientPO : clientPOList) {
                if(clientPO.getId().equals(row.getId())){
                    row.setCreator(clientPO.getCreator());
                }
            }
            //将拷贝完到row添加至VO中
            rows.add(row);
        }

 */
        //将数据拷贝到VO中
        for (ClientPO clientPO : clientPOList) {
            ClientVO.Client row = new ClientVO.Client();
            ClassCopyUtils.ClassCopy(row, clientPO);

            //遍历寻找creator并添加到VO中
            for (Clients.Client Crow : clients.getRows()) {
                if(Crow.getId().equals(row.getId())){
                    ClassCopyUtils.ClassCopy(row, Crow);
                    break;
                }
            }

            //将拷贝完到row添加至VO中
            rows.add(row);
        }
        //将rows写入rows中
        clientVO.setRows(rows);

        return CommonResult.success(clientVO);
    }

    @Override
    public CommonResult addClient(String remark, String vkey){
        try {
            if(vkey == null){
                vkey = "";
            }
            if(remark == null){
                remark = "";
            }
            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("remark", remark)
                    .add("vkey", vkey)
                    .add("config_conn_allow", "1")
                    .add("compress", "0")
                    .add("crypt", "0")
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+ip+":"+port+"/client/add")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @Override
    public CommonResult getTunnel(String id, String search) {
        try {
            if(search == null){
                search = "";
            }
            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("offset", "0")
                    .add("limit", "1000")
                    .add("client_id", id)
                    .add("search", search)
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+ip+":"+port+"/index/gettunnel")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            String string = Objects.requireNonNull(response.body()).string();
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> gets = objectMapper.readValue(string, HashMap.class);
            ArrayList<Object> rows = (ArrayList<Object>) gets.get("rows");
            HashMap<String, Object> data = new HashMap<>();
            ArrayList<HashMap<String, Object>> reRows = new ArrayList<>();
            for (Object row : rows) {
                HashMap<String, Object> temp = (HashMap<String, Object>) row;
                HashMap<String, Object> obj = new HashMap<>();
                obj.put("tunnelId", temp.get("Id"));
                obj.put("remark", temp.get("Remark"));
                obj.put("port", temp.get("Port"));
                obj.put("status", temp.get("Status"));
                obj.put("runStatus", temp.get("RunStatus"));
                obj.put("target", ((HashMap<String, Object>)temp.get("Target")).get("TargetStr"));
                reRows.add(obj);
            }
            data.put("rows", reRows);
            return CommonResult.success(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.fail(null);
    }

    @Override
    public CommonResult delClient(String id) {
        if (id == null) {
            return CommonResult.fail(null);
        }
        try {

            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("id", id)
                    .build();
            Request request = new Request.Builder()
                    .url("http://" + ip + ":" + port + "/client/del")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();

            client.newCall(request).execute();

            return CommonResult.success(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.fail(null);
    }

    @Override
    public CommonResult editClient(String id, String remark, String key) {
        try {
            if(id == null){
                return CommonResult.fail(null);
            }
            if(key == null){
                key = "";
            }
            if(remark == null){
                remark = "";
            }

            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("id", id)
                    .add("remark", remark)
                    .add("vkey", key)
                    .add("config_conn_allow", "1")
                    .add("compress", "0")
                    .add("crypt", "0")
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+ip+":"+port+"/client/edit")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @Override
    public CommonResult addTunnel(String id, String remark, String tunnelPort, String target) {
        try {
            if(id == null || tunnelPort == null || target == null){
                return CommonResult.fail(null);
            }
            if(remark == null){
                remark = "";
            }
            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("remark", remark)
                    .add("client_id", id)
                    .add("port", tunnelPort)
                    .add("target", target)
                    .add("type", "tcp")
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+ip+":"+port+"/index/add")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @Override
    public CommonResult delTunnel(String id) {
        if (id == null) {
            return CommonResult.fail(null);
        }
        try {
            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("id", id)
                    .build();
            Request request = new Request.Builder()
                    .url("http://" + ip + ":" + port + "/index/del")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();

            client.newCall(request).execute();

            return CommonResult.success(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.fail(null);
    }

    @Override
    public CommonResult editTunnel(String client_id, String id, String remark, String tunnelPort, String target) {
        try {
            if(client_id == null || id == null || tunnelPort == null || target == null){
                return CommonResult.fail(null);
            }
            if(remark == null){
                remark = "";
            }
            String COOKIE_beegosessionID = getCOOKIE_beegosessionID();
            FormBody formBody = new FormBody.Builder()
                    .add("id", id)
                    .add("remark", remark)
                    .add("client_id", client_id)
                    .add("port", tunnelPort)
                    .add("target", target)
                    .add("type", "tcp")
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+ip+":"+port+"/index/edit")
                    .addHeader("Cookie", COOKIE_beegosessionID)
                    .post(formBody)
                    .build();
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

}
