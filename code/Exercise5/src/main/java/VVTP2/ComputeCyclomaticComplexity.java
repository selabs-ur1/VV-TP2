package VVTP2;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.Expression;
import static com.github.javaparser.StaticJavaParser.*;

import java.util.List;

public class ComputeCyclomaticComplexity extends VoidVisitorWithDefaults<Void> {

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for (TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        if (!declaration.isPublic())
            return;
        System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));

        for (MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
        }
        // Printing nested types in the top level
        /*
         * for(BodyDeclaration<?> member : declaration.getMembers()) { if (member
         * instanceof TypeDeclaration) member.accept(this, arg); }
         */
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
        if (!declaration.isPublic())
            return;
        System.out.println("  " + declaration.getDeclarationAsString(true, true));

        int[] param = { 0, 0 }; // array of parameters : nodes, edges

        param = process(declaration, param);
        int N = param[0] + 1;
        int E = param[1];
        int cc = E - N + 2;
        System.out.println("  -- Cyclomatic Complexity :  " + cc);

    }

    /**
     * 
     * 
     * @param node
     * @param param
     * @return
     */
    private int[] process(Node node, int[] param) {

        if (node instanceof IfStmt) {
            param[0] += 2;
            param[1] += 3;

            if (((IfStmt) node).getElseStmt().isPresent()) {
                if (!(((IfStmt) node).getElseStmt().get() instanceof IfStmt)) {
                    param[0] += 1;
                    param[1] += 1;
                }
            }

        } else if (node instanceof WhileStmt || node instanceof DoStmt) {
            param[0] += 2;
            param[1] += 4;

        }

        else if (node instanceof SwitchEntry) {
            param[0] += 2;
            param[1] += 2;
        }

        else if (node instanceof ForStmt || node instanceof ForEachStmt) {
            param[0] += 3;
            param[1] += 5;
        }

        for (Node stmt : node.getChildNodes()) {
            // if (checkNodeStatement(stmt))
            param = process(stmt, param);
        }

        return param;
    }

    private boolean checkNodeStatement(Node node) {

        if (node instanceof AssertStmt || node instanceof ExpressionStmt
                || node instanceof ExplicitConstructorInvocationStmt || node instanceof LabeledStmt
                || node instanceof UnparsableStmt) {
            // System.out.println(node);
            return true;
        }
        return false;
    }
}