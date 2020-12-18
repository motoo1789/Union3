package test;
 
 
import java.util.ArrayList;
import java.util.List;
 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
 
public class SaveData {
	private Type st;
	private Name name;
	private int SourceStart;
	private int SourceEnd;
	private boolean AbstractBool;
	private ArrayList list = new ArrayList();
	private String AbstractName;
	private Object ExtendName ;
	private ArrayList block = new ArrayList();
	private ArrayList inblock = new ArrayList();
	private ArrayList<String> ImplementName = new ArrayList();
	private String ClassName;
	private ArrayList MethodModifier = new ArrayList();
	private String MethodName;
	private ArrayList MethodExpression = new ArrayList();
	private ArrayList AggregationVariableName = new ArrayList();
	private ArrayList AggregationMethodName = new ArrayList() {};
	private ArrayList<String> AggregationMethodVariableName = new ArrayList();
	private ArrayList ReturnDate = new ArrayList();
	private ArrayList TypeName = new ArrayList();
	private int er;
	
	public SaveData() {
	}
	
	public void setSourceStart(int start) {
		this.SourceStart = start;
	}
	
	public void setSourceEnd(int end) {
		this.SourceEnd = end;
	}
	
	public void setAbstractBool(boolean bool) {
		this.AbstractBool = bool;
	}
	public void setAbstractName(String name) {
		this.AbstractName = name;
	}
	public void setExtendName(Object s) {
		this.ExtendName = s.toString();
	}
	public void setImplementName(List list) {
		ImplementName.addAll(list);
	}
	
	public void setType(Type typename) {
		TypeName.add(typename);
		this.st = typename;
	}
	public void setClassName(Name name) {
		this.ClassName = name.toString();
	}
	
	public void setBlock(ASTNode ast) {
		block.add(ast.toString());
	}
	public void setInBlock(ASTNode ast) {
		inblock.add(ast.toString());
	}
	
	public void setMethodModifier(List list) {
		MethodModifier.add(list);
	}
	public void setMethodExpression(Expression expression) {
		MethodExpression.add(expression.toString());
	}
	public void setMethodName(String string) {
		this.MethodName = string;
	}
	public void setAggregationVariableName(Type type ,List list) {
		String sts = list.toString();
		String name = list.toString();
		if(name.contains("[")) {
			int index = name.indexOf("[");
			int end = name.length();
			sts = name.substring(index+1,end-1);
		}
		
		AggregationVariableName.add(type.toString());
		AggregationVariableName.add(sts);
 
	}
	public void setAggregationMethodName(Type type,List argument,String st,List ls) {
		AggregationMethodName.add(type.toString());
		AggregationMethodName.add(argument.toString());
		AggregationMethodName.add(st);
		AggregationMethodName.add(ls);
		
	}
	public void setAggregationMethodVariableName(Type type ,List list) {
 
		AggregationMethodVariableName.add(type.toString());
		AggregationMethodVariableName.add(list.toString());
	}
	public void setReturnDate(Expression ex) {
		ReturnDate.add(ex);
	}
	
	public void settest(int er) {
		this.er = er;
	}
	public  int getSourceStart() {
		return SourceStart;
	}
	public int getSourceEnd() {
		return SourceEnd;
	}
	
	public boolean getAbstractBool() {
		return AbstractBool;
	}
	public String getAbstractName() {
		return AbstractName;
	}
	public String getClassName() {
		return ClassName;
	}
	
	public ArrayList getBlock() {
		return block;
	}
	public ArrayList getInBlock() {
		return inblock;
	}
	public Object getExtendName() {
		return ExtendName;
	}
	public ArrayList getImplementName() {
		return ImplementName;
	}
	public ArrayList getMethodModifier() {
		return MethodModifier;
	}
	public ArrayList getMethodExpression() {
		return MethodExpression;
	}
	public String getMethodName() {
		return MethodName;
	}
	public ArrayList getAggregationVariableName() {
		return AggregationVariableName;
	}
	public ArrayList getAggregationMethodName() {
		return AggregationMethodName;
	}
	public ArrayList getAggregationMethodVariableName() {
		return AggregationMethodVariableName;
	}
	public ArrayList getReturnDate() {
		return ReturnDate;
	}
	public int gettest() {
		return er;
	}
}