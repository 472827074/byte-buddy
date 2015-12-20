package net.bytebuddy.description.type.generic;

import java.lang.reflect.Type;
import java.util.List;

public class GenericTypeListForLoadedTypesTest extends AbstractGenericTypeListTest<Type> {

    @Override
    protected Type getFirst() throws Exception {
        return Holder.class.getGenericInterfaces()[0];
    }

    @Override
    protected Type getSecond() throws Exception {
        return Holder.class.getGenericInterfaces()[1];
    }

    @Override
    protected GenericTypeList asList(List<Type> elements) {
        return new GenericTypeList.ForLoadedTypes(elements);
    }

    @Override
    protected GenericTypeDescription asElement(Type element) {
        return TypeDefinition.Sort.describe(element);
    }
}
