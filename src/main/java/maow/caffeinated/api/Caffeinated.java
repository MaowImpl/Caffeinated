package maow.caffeinated.api;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import maow.caffeinated.internal.parser.JavaLexer;
import maow.caffeinated.internal.parser.JavaParser;
import maow.caffeinated.internal.tree.visitor.ExpressionVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static maow.caffeinated.internal.parser.JavaParser.*;

public final class Caffeinated {
    private static String indent = "    ";

    private static CompilationUnitContext getCompilationUnit(Path input) throws IOException {
        final CharStream stream = CharStreams.fromPath(input);
        final JavaLexer lexer = new JavaLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final JavaParser parser = new JavaParser(tokens);
        return parser.compilationUnit();
    }

    private static List<TypeSpec> getSpecs(Path input) throws IOException {
        final CompilationUnitContext tree = getCompilationUnit(input);
        final List<TypeSpec> specs = new ArrayList<>();
        tree.typeDeclaration().forEach(ctx -> {
            final ExpressionVisitor visitor = new ExpressionVisitor();
            final TypeSpec spec = visitor.visitTypeDeclaration(ctx);
            if (spec != null) specs.add(spec);
        });
        return specs;
    }

    public static void write(String packageName, Path inputFile, Path outputDir) throws IOException {
        if (packageName == null) packageName = "";
        final List<TypeSpec> specs = getSpecs(inputFile);
        for (TypeSpec spec : specs) {
            JavaFile.builder(packageName, spec)
                    .indent(indent)
                    .build()
                    .writeTo(outputDir);
        }
    }

    public static void write(Path inputFile, Path outputDir) throws IOException {
        write("", inputFile, outputDir);
    }

    public static void print(String packageName, Path inputFile, Appendable out) throws IOException {
        if (packageName == null) packageName = "";
        final List<TypeSpec> specs = getSpecs(inputFile);
        for (TypeSpec spec : specs) {
            JavaFile.builder(packageName, spec)
                    .indent(indent)
                    .build()
                    .writeTo(out);
        }
    }

    public static void print(String packageName, Path inputFile) throws IOException {
        print(packageName, inputFile, System.out);
    }

    public static void print(Path inputFile, Appendable out) throws IOException {
        print("", inputFile, out);
    }

    public static void print(Path inputFile) throws IOException {
        print(inputFile, System.out);
    }

    public static void setIndent(String indent) {
        Caffeinated.indent = indent;
    }
}
