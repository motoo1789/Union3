package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import org.eclipse.core.resources.IResource;

import marker.GetResource;

public class RoleCounter {
	private static RoleCounter singleton = new RoleCounter();
	private static int rolecount = 1;

    private RoleCounter() {

    }

    public static RoleCounter getInstance() {
    	return singleton;
    }

    public static int getCount() {
    	return rolecount;
    }

    public static void setCount(int count) {
    	rolecount = count;
    }
}
