/******************************************************************************
 * Garbage Cat                                                                *
 *                                                                            *
 * Copyright (c) 2008-2010 Red Hat, Inc.                                      *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 *                                                                            *
 * Contributors:                                                              *
 *    Red Hat, Inc. - initial API and implementation                          *
 ******************************************************************************/
package org.eclipselabs.garbagecat.domain.jdk;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipselabs.garbagecat.util.jdk.JdkRegEx;
import org.eclipselabs.garbagecat.util.jdk.JdkUtil;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestCmsRemarkWithClassUnloadingEvent extends TestCase {

    public void testLogLine() {
        String logLine = "76694.727: [GC[YG occupancy: 80143 K (153344 K)]76694.727: [Rescan (parallel) , 0.0574180 secs]"
                + "76694.785: [weak refs processing, 0.0170540 secs]76694.802: [class unloading, 0.0363010 secs]76694.838: "
                + "[scrub symbol & string tables, 0.0276600 secs] [1 CMS-remark: 443542K(4023936K)] 523686K(4177280K), "
                + "0.1446880 secs]";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.CMS_REMARK_WITH_CLASS_UNLOADING.toString() + ".", CmsRemarkWithClassUnloadingEvent.match(logLine));
        CmsRemarkWithClassUnloadingEvent event = new CmsRemarkWithClassUnloadingEvent(logLine);
        Assert.assertEquals("Time stamp not parsed correctly.", 76694727, event.getTimestamp());
        Assert.assertEquals("Duration not parsed correctly.", 144, event.getDuration());
    }

    public void testLogLineWhitespaceAtEnd() {
        String logLine = "76694.727: [GC[YG occupancy: 80143 K (153344 K)]76694.727: [Rescan (parallel) , 0.0574180 secs]"
                + "76694.785: [weak refs processing, 0.0170540 secs]76694.802: [class unloading, 0.0363010 secs]76694.838: "
                + "[scrub symbol & string tables, 0.0276600 secs] [1 CMS-remark: 443542K(4023936K)] 523686K(4177280K), "
                + "0.1446880 secs]     ";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.CMS_REMARK_WITH_CLASS_UNLOADING.toString() + ".", CmsRemarkWithClassUnloadingEvent.match(logLine));
    }
    
    public void testLogLineWithTimesData() {
        String logLine = "76694.727: [GC[YG occupancy: 80143 K (153344 K)]76694.727: [Rescan (parallel) , 0.0574180 secs]"
                + "76694.785: [weak refs processing, 0.0170540 secs]76694.802: [class unloading, 0.0363010 secs]76694.838: "
                + "[scrub symbol & string tables, 0.0276600 secs] [1 CMS-remark: 443542K(4023936K)] 523686K(4177280K), "
                + "0.1446880 secs] [Times: user=0.13 sys=0.00, real=0.07 secs]";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.CMS_REMARK_WITH_CLASS_UNLOADING.toString() + ".", CmsRemarkWithClassUnloadingEvent.match(logLine));
        CmsRemarkWithClassUnloadingEvent event = new CmsRemarkWithClassUnloadingEvent(logLine);
        Assert.assertEquals("Time stamp not parsed correctly.", 76694727, event.getTimestamp());
        Assert.assertEquals("Duration not parsed correctly.", 144, event.getDuration());
    }

    public void testLogLineJdk7() {
        String logLine = "75.500: [GC[YG occupancy: 163958 K (306688 K)]75.500: [Rescan (parallel) , 0.0491823 secs]"
                + "75.549: [weak refs processing, 0.0088472 secs]75.558: [class unloading, 0.0049468 secs]75.563: "
                + "[scrub symbol table, 0.0034342 secs]75.566: [scrub string table, 0.0005542 secs] [1 CMS-remark: "
                + "378031K(707840K)] 541989K(1014528K), 0.0687411 secs]";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.CMS_REMARK_WITH_CLASS_UNLOADING.toString() + ".", CmsRemarkWithClassUnloadingEvent.match(logLine));
        CmsRemarkWithClassUnloadingEvent event = new CmsRemarkWithClassUnloadingEvent(logLine);
        Assert.assertEquals("Time stamp not parsed correctly.", 75500, event.getTimestamp());
        Assert.assertEquals("Duration not parsed correctly.", 68, event.getDuration());
    } 
    public void testLogLineJdk7WithTimesData() {
        String logLine = "75.500: [GC[YG occupancy: 163958 K (306688 K)]75.500: [Rescan (parallel) , 0.0491823 secs]"
                + "75.549: [weak refs processing, 0.0088472 secs]75.558: [class unloading, 0.0049468 secs]75.563: "
                + "[scrub symbol table, 0.0034342 secs]75.566: [scrub string table, 0.0005542 secs] [1 CMS-remark: "
                + "378031K(707840K)] 541989K(1014528K), 0.0687411 secs] [Times: user=0.13 sys=0.00, real=0.07 secs]";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.CMS_REMARK_WITH_CLASS_UNLOADING.toString() + ".", CmsRemarkWithClassUnloadingEvent.match(logLine));
    }
    
    public void testLogLineJdk8WithTrigger() {
        String logLine = "13.758: [GC (CMS Final Remark) [YG occupancy: 235489 K (996800 K)]13.758: [Rescan (parallel) ,"
                + " 0.0268664 secs]13.785: [weak refs processing, 0.0000365 secs]13.785: [class unloading, 0.0058936 secs]"
                + "13.791: [scrub symbol table, 0.0081277 secs]13.799: [scrub string table, 0.0007018 secs]"
                + "[1 CMS-remark: 0K(989632K)] 235489K(1986432K), 0.0430349 secs] [Times: user=0.36 sys=0.00, real=0.04 secs]";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.CMS_REMARK_WITH_CLASS_UNLOADING.toString() + ".", CmsRemarkWithClassUnloadingEvent.match(logLine));
        CmsRemarkWithClassUnloadingEvent event = new CmsRemarkWithClassUnloadingEvent(logLine);
        Assert.assertEquals("Time stamp not parsed correctly.", 13758, event.getTimestamp());
        Assert.assertTrue("Trigger not parsed correctly.", event.getTrigger().matches(JdkRegEx.TRIGGER_CMS_FINAL_REMARK));
        Assert.assertEquals("Duration not parsed correctly.", 43, event.getDuration());
    }    
}
