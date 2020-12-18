package test;
 
 
import java.awt.List;
import java.util.ArrayList;
 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.IntersectionType;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.ModuleDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NameQualifiedType;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.ProvidesDirective;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.RequiresDirective;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.UsesDirective;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
 
public class SimpleVisitor extends ASTVisitor{
//	private static final TypeDeclaration SimpleType = null;
	public  boolean ss;
	private int i =  0;
	private boolean s1 ;
	private boolean s2;
	private String filename;
	private Type supperName;
	private String ClassName;
	private String st;
	private boolean flag;
	private boolean flag2;
	private Type Block;
 
	ArrayList<SaveData> Data = new ArrayList<SaveData>();
	
	SaveData SD = new SaveData();
 
	public SimpleVisitor(String filename) {
		this.filename = filename;
		int index = filename.indexOf(".");
		 st = filename.substring(0,index );
 
		SD.setAbstractName(st);
		Data.add(SD);
		
	}
	public SaveData getSD() {
		return SD;
	}

    public boolean visit(TypeDeclaration node) {
        // クラス名を変更する
        setClassName(node , st);
        // 親クラスを変更する
        setSuperClass(node, "java.applet.Applet");
        return super.visit(node);
    }
    public boolean visit(SimpleName node) {
    	 return super.visit(node);
    }
 
    
    public boolean visit(NameQualifiedType node) {
    	return super.visit(node);
    }
 
	public boolean visit(Modifier node) {
		i++;
		String st = "hey";
		if(node.isAbstract() == true ) {
			SD.setAbstractBool(node.isAbstract());				
		}
		return super.visit(node);
	}
    private void setSuperClass(TypeDeclaration node, String superClassName) {
        this.supperName = node.getSuperclassType();
        Object s = node.getSuperclassType();
		try {
			if(s != null) SD.setExtendName(s);
			if(node.superInterfaceTypes() != null) SD.setImplementName(node.superInterfaceTypes());
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
 
        
    }
    private void setClassName(TypeDeclaration node , String string) {
    	
        AST ast = node.getAST();
        SimpleName simplename = ast.newSimpleName(string);
		try {
			if(node.getName() != null) SD.setClassName(node.getName());
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
        
        
    }

//	@SuppressWarnings("deprecation")
	public boolean visit(MethodDeclaration node) {
		SD.setMethodModifier(node.modifiers());
		SD.setMethodName(node.getName().getIdentifier());
		try {
			if(node.getReturnType2() != null && node.parameters() != null &&node.getName().getIdentifier() != null && node.modifiers() != null) 
				SD.setAggregationMethodName(node.getReturnType2(),node.parameters(),node.getName().getIdentifier(),node.modifiers());
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		this.ClassName = node.getName().getIdentifier();
		return super.visit(node);
	}
    
    public boolean visit(ExpressionStatement node) {
    	SD.setMethodExpression(node.getExpression());
    	return super.visit(node);
    }
    
 
    
    //this,super
    public boolean visit(FieldAccess node) {
    	return super.visit(node);
    }
    public boolean visit(FieldDeclaration node) {
    	Type type = node.getType();
    	int Start = node.getStartPosition();
    	int End = Start + type.getLength() - 1;
    	SD.setAggregationVariableName(node.getType() , node.fragments());
    	SD.setSourceStart(Start);
    	SD.setSourceEnd(End);
    	return super.visit(node);
    }
    public boolean visit(SingleVariableDeclaration node) {
    	return super.visit(node);
    }
    public boolean visit(RequiresDirective node) {
    	return super.visit(node);
    }
    
	public boolean visit(VariableDeclarationExpression node) {
		return super.visit(node);
	}
	
	public boolean visit(VariableDeclarationFragment node) {
		return super.visit(node);
	}
	public boolean visit(CastExpression node) {
		return super.visit(node);
	}
	
	public boolean visit(ReturnStatement node) {
		try {
			if(node.getExpression() != null) SD.setReturnDate(node.getExpression());;
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}
 
	
	public boolean visit(VariableDeclarationStatement node) {		
		SD.setAggregationMethodVariableName(node.getType(), node.fragments());
		return super.visit(node);
	}
 
	public boolean visit(Block node) {
		SD.setBlock(node.getParent());
		SD.setInBlock(node);
		return super.visit(node);
	}
}