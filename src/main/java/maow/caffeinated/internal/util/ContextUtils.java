package maow.caffeinated.internal.util;

import java.util.List;
import java.util.function.BiConsumer;

import static maow.caffeinated.internal.parser.JavaParser.*;

public final class ContextUtils {
    public static String[] getModifiers(List<ModifierContext> contexts) {
        final int size = contexts.size();
        final String[] mods = new String[size];
        for (int i = 0; i < size; i++) {
            final ModifierContext ctx = contexts.get(i);
            mods[i] = ctx.getText();
        }
        return mods;
    }

    public static void forEachField(ClassBodyContext ctx, TriConsumer<String[], String, FieldDeclarationContext> consumer) {
        final List<ClassBodyDeclarationContext> declarationContexts = ctx.classBodyDeclaration();
        for (ClassBodyDeclarationContext declarationCtx : declarationContexts) {
            final String[] modifiers = getModifiers(declarationCtx.modifier());
            final FieldDeclarationContext fieldCtx = declarationCtx.memberDeclaration().fieldDeclaration();
            if (fieldCtx != null) {
                final String type = fieldCtx.typeType().getText();
                consumer.accept(modifiers, type, fieldCtx);
            }
        }
    }

    public static void forEachVariable(FieldDeclarationContext ctx, BiConsumer<String, String> biConsumer) {
        final List<VariableDeclaratorContext> varContexts = ctx.variableDeclarators().variableDeclarator();
        varContexts.forEach(varCtx -> {
            final String id = varCtx.variableDeclaratorId().IDENTIFIER().getText();
            final VariableInitializerContext varInitCtx = varCtx.variableInitializer();
            final String value = (varInitCtx == null) ? "" : varInitCtx.getText();
            biConsumer.accept(id, value);
        });
    }
}
