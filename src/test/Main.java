package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.core.nd.field.FieldList;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import MatchProcess.Display;
import MatchProcess.IdDocking;
import MatchProcess.Initialization;
import SelectPattern.*;
import database.RoleCounter;
import marker.GetResource;
import views.View;




public class Main {
	private int lim;


    public void process() throws IOException, PartInitException {

    	View view = (View) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("Union3.ErrorView");
		String directory = view.getDirectory();

    	File file = new File(directory);
    	//".././FactoryMethod_review/src/Lack"
    	//G://ゼミ//プログラム//huston//src/composite

    	File filelist[] = file.listFiles();

        final ASTParser parser = ASTParser.newParser(AST.JLS10);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        // 特徴抽出
        FeatureEvaluation FE = new FeatureEvaluation();

        for(int i=0; i<filelist.length; i++) {

        	parser.setSource(
            FileUtils.readFileToString(filelist[i], "UTF-8").toCharArray());
        	parser.setResolveBindings(true);


        	final CompilationUnit node = (CompilationUnit) parser.createAST(new NullProgressMonitor());
        	final SimpleVisitor visitor = new SimpleVisitor(filelist[i].getName());
        	node.accept(visitor);
        	FE.setSaveInstance(visitor.getSD());

        }

        try {
        	  File f = new File("C:\\pleiades10月13日\\pleiades\\workspace\\Union3\\Result\\result.txt");

        	  f.delete();
        	  f.createNewFile();

        	} catch(IOException e) {
        	  System.out.println(e);
        	}
        // 特徴を保存
        FE.register();



        // 選択したパターンを入れる場所
        String test = view.getSelectPattern();;
        // Select Pattern save in DB
        Pattern_IO pio = new Pattern_IO();
        pio.p_write(test);

        // 選択したパターンに必要なクラスの組み合わせを作成
        Map<Integer,Strategy> selectpattern = new HashMap<>();
        selectpattern.put(2, new SelectPattern_2());
        selectpattern.put(3, new SelectPattern_3());
        selectpattern.put(4, new SelectPattern_4());
        selectpattern.put(5, new SelectPattern_5());

        SelectPattern_DB(test);
        Select s = new Select(selectpattern.get(lim));
        s.fileprocess(filelist);
        s.ncombination();
        s.provisionalnumber();

//        // 最後の処理　パターンエラーの原因と修正を表示する
//        Display dis = new Display();
//        dis.show();

        RoleCounter counterObject = RoleCounter.getInstance();
        counterObject.setCount(1);

        //ErrorSizeから一番小さいものをWTF_tableへ入れる
        IdDocking id = IdDocking.getInstance();
        id.insertWTF_table();
        id.clearList();
    }

    public void SelectPattern_DB(String pattern) {
		Connection conn = null;

		//dbのディレクトリ取得
		String projectName = GetResource.getProjectname();
		IResource resouece = GetResource.getResource(projectName);
		String dbPath= GetResource.getDatabase(resouece);


	    try {
	        conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	        String ssw ="DELETE from WTF_table";
	        String sw ="DELETE from Role";
	        Statement s5t = conn.createStatement();
	        s5t.executeUpdate(ssw);
	        s5t.executeUpdate(sw);
	        s5t.close();
	        //ErrorテーブルのDELETE
	        Initialization inza = new Initialization();
	        inza.initaialization();


	        String sql ="select * from PatternInformation";
	        PreparedStatement p = conn.prepareStatement(sql);
	        ResultSet re = p.executeQuery();
	        while(re.next()) {
	        	if(re.getString("DesignPattern").equals(pattern)) {
	        		this.lim=re.getInt("Lim");
	        	}
	        }

	    } catch (SQLException se) {
	        System.out.println(se.getMessage());
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	            }
	        }
	    }
    }
}