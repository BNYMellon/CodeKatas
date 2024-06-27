/*
 * Copyright 2024 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bny.codekatas.lambdakata.ec;

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
