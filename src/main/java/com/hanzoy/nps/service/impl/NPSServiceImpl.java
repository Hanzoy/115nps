package com.hanzoy.nps.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanzoy.nps.aop.Function;
import com.hanzoy.nps.domain.Client;
import com.hanzoy.nps.mapper.TunnelMapper;
import com.hanzoy.nps.pojo.bo.ClientBO;
import com.hanzoy.nps.pojo.bo.TokenBO;
import com.hanzoy.nps.pojo.dto.CommonResult;
import com.hanzoy.nps.pojo.dto.npsDto.Clients;
import com.hanzoy.nps.mapper.ClientMapper;
import com.hanzoy.nps.pojo.dto.npsDto.Tunnels;
import com.hanzoy.nps.pojo.po.ClientPO;
import com.hanzoy.nps.pojo.po.TunnelPO;
import com.hanzoy.nps.pojo.vo.TunnelVO;
import com.hanzoy.nps.service.NPSService;
import com.hanzoy.nps.service.UserService;
import com.hanzoy.nps.utils.ClassCopyUtils.ClassCopyUtils;
import com.hanzoy.nps.pojo.vo.ClientVO;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
@ConfigurationProperties(prefix = "nps")
public class NPSServiceImpl implements NPSService {

    @Autowired
    UserService userService;

    @Resource
    ClientMapper clientMapper;

    @Resource
    TunnelMapper tunnelMapper;
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
    @Function("???????????????")
    public CommonResult getClientList(String search, String token) {
        //token????????????AOP??????

        //????????????????????????clients
        Clients clients = getClientList("");

        //???????????????????????????
//        ArrayList<ClientPO> clientPOList = clientMapper.selectAllClient();
        ArrayList<ClientPO> clientPOList = clientMapper.selectClient(search);

        ClientVO clientVO = new ClientVO();
        ArrayList<ClientVO.Client> rows = new ArrayList<>();
/*
        //??????????????????VO???
        for (Clients.Client Crow : clients.getRows()) {

            ClientVO.Client row = new ClientVO.Client();
            ClassCopyUtils.ClassCopy(row, Crow);
            //????????????creator????????????VO???
            for (ClientPO clientPO : clientPOList) {
                if(clientPO.getId().equals(row.getId())){
                    row.setCreator(clientPO.getCreator());
                }
            }
            //???????????????row?????????VO???
            rows.add(row);
        }

 */
        //??????????????????VO???
        for (ClientPO clientPO : clientPOList) {
            ClientVO.Client row = new ClientVO.Client();
            ClassCopyUtils.ClassCopy(row, clientPO);

            //????????????creator????????????VO???
            for (Clients.Client Crow : clients.getRows()) {
                if(Crow.getId().equals(row.getId())){
                    ClassCopyUtils.ClassCopy(row, Crow);
                    break;
                }
            }

            //???????????????row?????????VO???
            rows.add(row);
        }
        //???rows??????rows???
        clientVO.setRows(rows);

        return CommonResult.success(clientVO);
    }

    @Override
    @Function("???????????????")
    public CommonResult addClient(String remark, String vkey, String token){
        //token????????????AOP??????

        try {
            if(vkey == null){
                vkey = "";
            }
            if(remark == null){
                remark = "";
            }

            if(clientMapper.selectClientIdByKey(vkey) != null)
                return CommonResult.fail("A0400", "???????????????key??????");

            //??????token??????
            TokenBO tokenInfo = userService.getTokenInfo(token);

            //??????vkey?????????????????????uuid
            if(vkey.equals(""))
                vkey = UUID.randomUUID().toString().replace("-", "");

            //???????????????????????????
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

            //???????????????????????????id
            Integer id = getClientList(vkey).getRows().get(0).getId();

            //??????????????????client
            Client client = new Client();
            client.setId(id);
            client.setCreator(tokenInfo.getId());
            client.setKey(vkey);
            client.setRemark(remark);

            //???????????????
            clientMapper.insertClient(client);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @Override
    public Tunnels getTunnelList(String id, String search) {
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
//            HashMap<String, Object> data = new HashMap<>();
//            ArrayList<HashMap<String, Object>> reRows = new ArrayList<>();
            Tunnels tunnels = new Tunnels();
            ArrayList<Tunnels.Tunnel> tunnelRows = new ArrayList<>();
            for (Object row : rows) {
                HashMap<String, Object> temp = (HashMap<String, Object>) row;
                Tunnels.Tunnel tunnel = new Tunnels.Tunnel();
                tunnel.setId((Integer) temp.get("Id"));
                tunnel.setRemark((String) temp.get("Remark"));
                tunnel.setPort(temp.get("Port").toString());
                tunnel.setStatus(temp.get("Status").toString());
                tunnel.setRunStatus(temp.get("RunStatus").toString());
                tunnel.setTarget(((HashMap<String, String>)temp.get("Target")).get("TargetStr"));
                tunnelRows.add(tunnel);
            }
            tunnels.setRows(tunnelRows);
            return tunnels;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Function("????????????")
    public CommonResult getTunnelList(String id, String search, String token) {
        //token????????????AOP??????

        //??????????????????
        Tunnels tunnels = getTunnelList(id, "");

        //???????????????????????????
        ArrayList<TunnelPO> tunnelPOS = tunnelMapper.selectTunnel(new Integer(id), search);

        TunnelVO tunnelVo = new TunnelVO();
        ArrayList<TunnelVO.Tunnel> rows = new ArrayList<>();

        //??????????????????VO
        for (TunnelPO tunnelPO : tunnelPOS) {
            TunnelVO.Tunnel row = new TunnelVO.Tunnel();
            ClassCopyUtils.ClassCopy(row, tunnelPO);
            for (Tunnels.Tunnel tunnelsRow : tunnels.getRows()) {
                if(tunnelsRow.getId().equals(row.getId())){
                    ClassCopyUtils.ClassCopy(row, tunnelsRow);
                }
            }
            //????????????row?????????rows???
            rows.add(row);
        }
        tunnelVo.setRows(rows);
        return CommonResult.success(tunnelVo);
    }

    @Override
    @Function("???????????????")
    public CommonResult delClient(String id, String token) {
        //token????????????AOP??????

        if (id == null) {
            return CommonResult.fail(null);
        }
        try {
            //????????????????????????id????????????
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

            //?????????????????????
            clientMapper.deleteClient(new Integer(id));

            return CommonResult.success(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.fail(null);
    }

    @Override
    @Function("???????????????")
    public CommonResult editClient(String id, String remark, String key, String token) {
        //token????????????AOP??????

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

            //???????????????????????????
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

            //????????????client
            ClientBO clientBO = new ClientBO();
            clientBO.setId(new Integer(id));
            clientBO.setRemark(remark);
            clientBO.setKey(key);

            //???????????????
            clientMapper.updateClient(clientBO);

        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @Override
    @Function("????????????")
    public CommonResult addTunnel(String id, String remark, String tunnelPort, String target, String token) {
        //token????????????AOP??????

        TunnelPO tunnelPO = tunnelMapper.selectTunnelByPort(tunnelPort);

        if(tunnelPO != null)
            return CommonResult.fail("A0400", "???????????????????????????");
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

            //????????????????????????
            Tunnels tunnel = getTunnelList(id, tunnelPort);

            System.out.println(tunnel);

            if (tunnel.getRows().size() != 1){
                tunnel = getTunnelList(id, remark);
            }
            Tunnels.Tunnel resTunnel = tunnel.getRows().get(0);

            //??????token??????????????????
            TokenBO tokenInfo = userService.getTokenInfo(token);

            //?????????????????????
            tunnelMapper.insertTunnel(resTunnel.getId(), new Integer(id), tokenInfo.getId(), resTunnel.getRemark(), resTunnel.getPort(), resTunnel.getTarget());
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @Override
    @Function("????????????")
    public CommonResult delTunnel(String id, String token) {
        //token????????????AOP??????

        if (id == null) {
            return CommonResult.fail(null);
        }
        try {
            //??????????????????
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

            //?????????????????????
            tunnelMapper.deleteTunnel(id);

            return CommonResult.success(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.fail(null);
    }

    @Override
    @Function("????????????")
    public CommonResult editTunnel(String id, String client_id, String remark, String tunnelPort, String target, String token) {
        //token????????????AOP??????

        try {
            if(client_id == null || id == null || tunnelPort == null || target == null){
                return CommonResult.fail(null);
            }
            if(remark == null){
                remark = "";
            }
            //??????????????????
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

            //???????????????
            tunnelMapper.updateTunnel(id, remark, target, tunnelPort, client_id);

        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

}
