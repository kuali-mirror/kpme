package org.kuali.kpme.edo.util;

import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/13/12
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bool2BigDecimalOJBConversion implements FieldConversion {

        private static BigDecimal I_TRUE = new BigDecimal(1);
        private static BigDecimal I_FALSE = new BigDecimal(0);

        private static Boolean B_TRUE = new Boolean(true);
        private static Boolean B_FALSE = new Boolean(false);

        /**
         * @see FieldConversion#javaToSql(Object)
         */
        public Object javaToSql(Object source)
        {
            if (source instanceof Boolean)
            {
                if (source.equals(B_TRUE))
                {
                    return I_TRUE;
                }
                else
                {
                    return I_FALSE;
                }
            }
            else
            {
                return source;
            }
        }

        /**
         * @see FieldConversion#sqlToJava(Object)
         */
        public Object sqlToJava(Object source)
        {
            if (source instanceof BigDecimal)
            {
                if (source.equals(I_TRUE))
                {
                    return B_TRUE;
                }
                else
                {
                    return B_FALSE;
                }
            }
            else
            {
                return source;
            }
        }
}
