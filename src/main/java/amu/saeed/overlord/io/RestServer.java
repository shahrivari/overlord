package amu.saeed.overlord.io;

import amu.saeed.overlord.kv.KvStoreHub;
import amu.saeed.overlord.type.KeyValue;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

import static spark.Spark.*;

public class RestServer {
    int port = 1234;
    int threadsNum = 16;
    KvStoreHub kvStoreHub = new KvStoreHub();

    public static void main(String[] args) {
        RestServer restServer = new RestServer();
        restServer.kvStoreHub.put("123".getBytes(), "SALAAAAAM!!!!".getBytes());
        restServer.start();
    }

    public void start() {
        threadPool(threadsNum);
        port(port);

        get("/api/v1/raw/:hexKey", (request, response) -> {
            response.type("application/octet-stream");
            String hexKey = request.params(":hexKey");
            byte[] key = BaseEncoding.base16().decode(hexKey);
            byte[] val = kvStoreHub.get(key);
            if (val != null) {
                response.status(200);
                response.raw().getOutputStream().write(val);
                response.raw().getOutputStream().close();
                return response.raw();
            } else
                halt(404, "Key not found.");
            return null;
        });

        put("/api/v1/raw/:hexKey", (request, response) -> {
            response.type("application/octet-stream");
            String hexKey = request.params(":hexKey");
            byte[] key = BaseEncoding.base16().decode(hexKey);
            byte[] val = ByteStreams.toByteArray(request.raw().getInputStream());
            kvStoreHub.put(key, val);
            response.status(200);
            return "OK";
        });

        get("/api/v1/json/:hexKey", (request, response) -> {
            response.type("text/json");
            String hexKey = request.params(":hexKey");
            byte[] key = BaseEncoding.base16().decode(hexKey);
            byte[] val = kvStoreHub.get(key);
            if (val != null) {
                response.status(200);
                return new KeyValue(key, val).toJson();
            } else
                halt(404, "Key not found.");
            return null;
        });

        awaitInitialization();
    }
}
