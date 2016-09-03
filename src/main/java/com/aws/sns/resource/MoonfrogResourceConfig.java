/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2014 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.aws.sns.resource;

import java.util.HashMap;
import java.util.Map;

import javax.json.stream.JsonGenerator;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.aws.sns.resource.listener.MoonfrogApplicationEventListener;
import com.aws.sns.resource.security.RestAuthTokenFilter;

/**
 * {@link javax.ws.rs.core.Application} descendant.
 *
 * Used to set resource and providers classes.
 *
 * @author Vinod
 */
public class MoonfrogResourceConfig extends ResourceConfig {
    public MoonfrogResourceConfig() {
        /*super(	
        		UserResource.class,
                // register Jackson ObjectMapper resolver
                JacksonFeature.class
        );*/
        packages("com.moonfrog.halfbyte.resource");
        register(JacksonFeature.class);
        //register filters
        register(RestAuthTokenFilter.class);
        register(MoonfrogApplicationEventListener.class);
        Map<String,Object> propsMap = new HashMap<String, Object>(1);
        
        propsMap.put(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, true);
        addProperties(propsMap);
        
        property(JsonGenerator.PRETTY_PRINTING, true);
    }
}
