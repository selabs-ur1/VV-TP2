package VVTP2;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;



public class PrivateFieldsGetter extends VoidVisitorWithDefaults<Void>{
    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
       //super.visit(declaration, arg);
       String pkgName = declaration.getFullyQualifiedName().orElse("[Anonymous]");
       String [] pkg = pkgName.split("\\.");
       if(pkg.length>1){
        pkgName = pkgName.substring(0, pkgName.length() - pkg[pkg.length - 1].length() - 1);
       } else {pkgName = "No package";}
       
       
       String text = "-- Package : " + pkgName + "\n---- Class : "+declaration.getName();
       int len1 = text.length();
        int len2 = text.length();
            for(FieldDeclaration attribute:declaration.getFields()){
                if(!attribute.isPublic()) {

                String name = attribute.getVariable(0).getName().toString();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                name = "get"+name;
                boolean state = true;
                for(MethodDeclaration method : declaration.getMethods()) {
                   if(name.equals(method.getName().toString())){
                       
                        state = false;
                        break;
                   } 
                    

                }
                if (state){
                    text += "\n------ Attribute : "+attribute.getVariable(0).getName();
                }
            }
            if(text.length() > len1){
                len1 = text.length();
            }

        }
        if (len1 > len2){
            try {
                FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/private_fields.txt", true);
                text += "\n"; 
                BufferedWriter bw=new BufferedWriter(fw);
                bw.append(text);
                bw.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            System.out.println(text);
        }
    }

}