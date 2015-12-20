package net.bytebuddy.description.type.generic;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.test.utility.ObjectPropertyAssertion;
import org.junit.Test;

import java.lang.reflect.Type;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenericTypeDescriptionTest {

    @Test
    public void testObjectProperties() throws Exception {
        ObjectPropertyAssertion.of(TypeDefinition.Sort.class).apply();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownType() throws Exception {
        TypeDefinition.Sort.describe(mock(Type.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeArityGenericArray() throws Exception {
        GenericTypeDescription.ForGenericArray.Latent.of(mock(GenericTypeDescription.class), -1);
    }

    @Test
    public void testZeroArityReturnsInstance() throws Exception {
        GenericTypeDescription genericTypeDescription = mock(GenericTypeDescription.class);
        when(genericTypeDescription.getSort()).thenReturn(TypeDefinition.Sort.PARAMETERIZED);
        assertThat(GenericTypeDescription.ForGenericArray.Latent.of(genericTypeDescription, 0), is(genericTypeDescription));
    }

    @Test
    public void testNonGenericArrayType() throws Exception {
        TypeDescription typeDescription = mock(TypeDescription.class);
        when(typeDescription.asErasure()).thenReturn(typeDescription);
        when(typeDescription.getSort()).thenReturn(TypeDefinition.Sort.NON_GENERIC);
        assertThat(GenericTypeDescription.ForGenericArray.Latent.of(typeDescription, 1).getSort(), is(TypeDefinition.Sort.NON_GENERIC));
        assertThat(GenericTypeDescription.ForGenericArray.Latent.of(typeDescription, 1).getComponentType(), is((GenericTypeDescription) typeDescription));
    }

    @Test
    public void testZeroArityIteratesTypesInstance() throws Exception {
        GenericTypeDescription genericTypeDescription = mock(GenericTypeDescription.class);
        GenericTypeDescription componentType = mock(GenericTypeDescription.class);
        when(genericTypeDescription.getComponentType()).thenReturn(componentType);
        when(genericTypeDescription.getSort()).thenReturn(TypeDefinition.Sort.GENERIC_ARRAY);
        when(componentType.getSort()).thenReturn(TypeDefinition.Sort.PARAMETERIZED);
        GenericTypeDescription result = GenericTypeDescription.ForGenericArray.Latent.of(genericTypeDescription, 1);
        assertThat(result.getSort(), is(TypeDefinition.Sort.GENERIC_ARRAY));
        assertThat(result.getComponentType().getSort(), is(TypeDefinition.Sort.GENERIC_ARRAY));
        assertThat(result.getComponentType().getComponentType(), is(componentType));
    }

    @Test
    public void testZeroArityIteratesTypesInstanceNonGeneric() throws Exception {
        GenericTypeDescription genericTypeDescription = mock(GenericTypeDescription.class);
        GenericTypeDescription componentType = mock(GenericTypeDescription.class);
        when(genericTypeDescription.getComponentType()).thenReturn(componentType);
        when(genericTypeDescription.getSort()).thenReturn(TypeDefinition.Sort.GENERIC_ARRAY);
        when(componentType.getSort()).thenReturn(TypeDefinition.Sort.NON_GENERIC);
        GenericTypeDescription result = GenericTypeDescription.ForGenericArray.Latent.of(genericTypeDescription, 1);
        assertThat(result.getSort(), is(TypeDefinition.Sort.NON_GENERIC));
        assertThat(result.getComponentType().getSort(), is(TypeDefinition.Sort.NON_GENERIC));
        assertThat(result.getComponentType().getComponentType(), is(componentType));
    }

    @Test
    public void testEquality() throws Exception {
        assertThat(TypeDefinition.Sort.NON_GENERIC.isNonGeneric(), is(true));
        assertThat(TypeDefinition.Sort.WILDCARD.isWildcard(), is(true));
        assertThat(TypeDefinition.Sort.PARAMETERIZED.isParameterized(), is(true));
        assertThat(TypeDefinition.Sort.VARIABLE_DETACHED.isDetachedTypeVariable(), is(true));
        assertThat(TypeDefinition.Sort.VARIABLE.isTypeVariable(), is(true));
        assertThat(TypeDefinition.Sort.VARIABLE_SYMBOLIC.isSymbolicTypeVariable(), is(true));
        assertThat(TypeDefinition.Sort.GENERIC_ARRAY.isGenericArray(), is(true));
    }
}
