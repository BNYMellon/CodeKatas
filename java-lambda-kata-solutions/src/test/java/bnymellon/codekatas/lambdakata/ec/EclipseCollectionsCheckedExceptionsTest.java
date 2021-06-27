/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.lambdakata.ec;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EclipseCollectionsCheckedExceptionsTest
{
    @Test
    public void dependableAppendableWithCheckedProcedure()
    {
        Appendable appendable = new StringBuilder();
        MutableList<String> list = Lists.mutable.with("1", "2", "3");
        list.forEach(Procedures.throwing(each -> appendable.append(each)));
        // Method reference
        // list.forEach(Procedures.throwing(appendable::append));
        Assertions.assertEquals("123", appendable.toString());
    }
}
