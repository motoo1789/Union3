package makererrormap;


public abstract class Creator {
	public ProductTable createTable() {
		ProductTable producttable = createProduct();
		return producttable;
	}
	public abstract ProductTable createProduct();
}
