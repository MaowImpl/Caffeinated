package maow.caffeinated.internal.util;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import maow.javapoethacks.JavaPoetUtils;

import javax.lang.model.element.Modifier;

public final class PoetUtils {
    public static Modifier[] getModifiers(String[] strings) {
        final int length = strings.length;
        final Modifier[] modifiers = new Modifier[length];
        for (int i = 0; i < length; i++) {
            final String s = strings[i].toUpperCase();
            modifiers[i] = Modifier.valueOf(s);
        }
        return modifiers;
    }

    public static FieldSpec field(String[] modifierStrings, String typeString, String name, String value) {
        final TypeName type = JavaPoetUtils.from(typeString);
        final Modifier[] modifiers = getModifiers(modifierStrings);
        return FieldSpec.builder(type, name)
                .addModifiers(modifiers)
                .initializer(value)
                .build();
    }

    public static MethodSpec setter(String typeString, String name) {
        final TypeName type = JavaPoetUtils.from(typeString);
        return MethodSpec.methodBuilder("set" + capitalize(name))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(type, name)
                .addStatement("this.$L = $L", name, name)
                .build();
    }

    public static MethodSpec getter(String typeString, String name) {
        final TypeName type = JavaPoetUtils.from(typeString);
        return MethodSpec.methodBuilder("get" + capitalize(name))
                .addModifiers(Modifier.PUBLIC)
                .returns(type)
                .addStatement("return $L", name)
                .build();
    }

    public static MethodSpec.Builder ctor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);
    }

    public static ParameterSpec param(String typeString, String name) {
        final TypeName type = JavaPoetUtils.from(typeString);
        return ParameterSpec.builder(type, name)
                .build();
    }

    private static String capitalize(String s) {
        if (s != null && !s.isEmpty()) {
            final char letter = s.charAt(0);
            return Character.toUpperCase(letter) + s.substring(1);
        }
        return "";
    }
}
