package fr.istic.vv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import com.github.javaparser.utils.Pair;



// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class PublicElementsPrinter extends VoidVisitorWithDefaults<Void> {
	
	Map<String, Pair<List<FieldDeclaration>, List<MethodDeclaration>>> relations;
	
	public PublicElementsPrinter() {
		relations = new HashMap<String, Pair<List<FieldDeclaration>,List<MethodDeclaration>>>();
	}
	
    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
    	

    	List<FieldDeclaration> privateFields = new ArrayList<>();
    	List<MethodDeclaration> methodes = new ArrayList<>();
    	
        //System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));
        // Printing nested types in the top level
        for(BodyDeclaration<?> member : declaration.getMembers()) {
        	if(member instanceof MethodDeclaration) methodes.add((MethodDeclaration) member);
        	if(member instanceof FieldDeclaration && ((FieldDeclaration)member).isPrivate()) privateFields.add((FieldDeclaration) member);
            member.accept(this, arg);
        }
        
        Pair<List<FieldDeclaration>, List<MethodDeclaration>> p;
        p = new Pair<List<FieldDeclaration>, List<MethodDeclaration>>(privateFields, methodes);
        String name = declaration.getFullyQualifiedName().orElse("[Anonymous]");
        relations.put(name, p);
        
        
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(!declaration.isPublic()) return;
        //System.out.println("  " + declaration.getDeclarationAsString(true, true));
        }
    
    @Override
    public void visit(ImportDeclaration declaration, Void arg) {
    	//System.out.println(declaration.toString());
    }
    
    @Override
    public void visit(FieldDeclaration declaration, Void arg) {
    	//System.out.println(declaration.toString());
    }
    
    public void verificationAccess() {
    	for(Entry<String, Pair<List<FieldDeclaration>, List<MethodDeclaration>>> e : relations.entrySet()) {
    		String name = e.getKey();
    		List<FieldDeclaration> privateFields = e.getValue().a;
    		List<MethodDeclaration> methodes = e.getValue().b;
    		
    		System.out.println("In " + name + " : ");
    		
    		for(FieldDeclaration field : privateFields) {
    			for(VariableDeclarator v : field.getVariables()) {
    				String variableName = v.getName().toString();
    				boolean find = false;
    				
    				for(MethodDeclaration m : methodes) {
    					NodeList<Statement> statements = m.getBody().get().getStatements();
    					if(m.isPublic() && statements.size() == 1 && statements.get(0).isReturnStmt()) {
    						String returnName = ((ReturnStmt)statements.get(0)).getExpression().get().toString();
    						if(variableName.equals(returnName)) {
    							find = true;
    							break;
    						}
    					}
    				}
    				
    				if(!find) {
    					System.out.println(variableName + " is private and doesn't have any public getter");
    				}
    			}
    		}
    		
    	}
    }
}
