package jksj.homework.homework.week01;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ClassLoader extends java.lang.ClassLoader {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> helloClass = new ClassLoader().findClass("Hello");
        Object o = helloClass.newInstance();
        helloClass.getDeclaredMethod("hello").invoke(o);
    }

    @Override
    protected Class<?> findClass(String name){
        ClassPathResource classPathResource = new ClassPathResource("Hello.xlass");
        try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(classPathResource.getFile()))) {
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            return defineClass(name, decode(bytes), 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decode(byte[] bytes){
        for(int i=0;i<bytes.length;i++){
            bytes[i] = (byte)(255 - bytes[i]);
        }
        return bytes;
    }
}
