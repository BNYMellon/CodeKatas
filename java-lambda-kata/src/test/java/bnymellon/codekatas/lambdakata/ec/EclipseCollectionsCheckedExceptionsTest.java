  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

package bnymellon.codekatas.lambdakata.ec;

import java.io.IOException;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.procedure.checked.CheckedProcedure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EclipseCollectionsCheckedExceptionsTest
{
    @Test
    public void dependableAppendableWithCheckedProcedure()
    {
        Appendable appendable = new StringBuilder();
        MutableList<String> list = Lists.mutable.with("1", "2", "3");
        // Replace the CheckedProcedure with a call to Procedures.throwing() using
        // a lambda or method reference
        list.forEach(new CheckedProcedure<>()
        {
            @Override
            public void safeValue(String each) throws IOException
            {
                appendable.append(each);
            }
        });
        Assertions.assertEquals("123", appendable.toString());
    }
}
