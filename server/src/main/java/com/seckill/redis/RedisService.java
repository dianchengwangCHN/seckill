package com.seckill.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

    private final JedisPool jedisPool;

    @Autowired
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * get method
     * */
    public <T> T get(KeyPrefix prefix, String key, Class<T> cls) throws IOException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            return stringToBean(str, cls);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * set method
     * */
    public <T> boolean set(KeyPrefix prefix, String key, T value) throws JsonProcessingException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * delete method
     * */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            long res = jedis.del(realKey);
            return res > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * incr method
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realkey = prefix.getPrefix() + key;
            return jedis.incr(realkey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * decr method
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
         try {
             jedis = jedisPool.getResource();
             String realKey = prefix.getPrefix() + key;
             return jedis.decr(realKey);
         } finally {
             returnToPool(jedis);
         }
    }

    /**
     * exists method
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean delete(KeyPrefix prefix) {
        if (prefix == null) {
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        if (keys == null || keys.size() <= 0) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*" + key + "*");
            sp.count(100);
            do {
                ScanResult<String> sr = jedis.scan(cursor, sp);
                List<String> result = sr.getResult();
                if (result != null && result.size() > 0) {
                    keys.addAll(result);
                }
                cursor = sr.getStringCursor();
            } while (!cursor.equals("0"));
            return keys;
        } finally {
            returnToPool(jedis);
        }
    }

    public static <T> String beanToString(T value) throws JsonProcessingException {
        if (value == null) {
            return null;
        }
        Class<?> cls = value.getClass();
        if (cls == int.class || cls == Integer.class) {
            return String.valueOf(value);
        } else if (cls == long.class || cls == Long.class) {
            return String.valueOf(value);
        } else if (cls == String.class) {
            return (String) value;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> cls) throws IOException {
        if (str == null || str.length() <= 0 || cls == null) {
            return null;
        }
        if (cls == int.class || cls == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (cls == String.class) {
            return (T) str;
        } else if (cls == long.class || cls == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(str, cls);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
