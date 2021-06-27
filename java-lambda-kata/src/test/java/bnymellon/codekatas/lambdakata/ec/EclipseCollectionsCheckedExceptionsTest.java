/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
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
