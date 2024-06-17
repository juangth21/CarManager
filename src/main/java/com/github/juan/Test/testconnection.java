package com.github.juan.Test;

import com.github.juan.Model.connection.ConnectionProperties;
import com.github.juan.utils.XMLManager;

public class testconnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
