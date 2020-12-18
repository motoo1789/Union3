package makererrormap;

import java.util.Map;


public interface ProductTable {

	abstract public void setWidget();
	public abstract void makeTableData(Map<String,String> tabledata);
	public abstract void setClassname(String role_id);
}
