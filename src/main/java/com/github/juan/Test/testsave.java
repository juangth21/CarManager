package com.github.juan.Test;

import com.github.juan.Model.connection.ConnectionProperties;
import com.github.juan.utils.XMLManager;

public class testsave {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3306","carmanager","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
