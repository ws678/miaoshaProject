package com.miaoshaproject.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//TypeParameter TypeUse
public class TestType<@TypeParam T> {

    public <@TypeParam T extends Integer> void test() {

    }

    private @NotNull int a = 10;

    public void testA(@NotNull String a, @NotNull int b) {

    }
}

@Target(ElementType.TYPE_PARAMETER)
@interface TypeParam {

}

@Target(ElementType.TYPE_USE)
@interface NotNull {

}
