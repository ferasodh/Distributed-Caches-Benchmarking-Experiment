/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.performance.model;


import java.io.Serializable;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.infinispan.protostream.annotations.ProtoField;

/**
      /**
      *
      * @author Haytham Salhi and Rabee Naser
      *
      */
@Indexed
     public class Organization implements Serializable {
         private static final long serialVersionUID = 553885034467846744L;
         
         @ProtoField(number = 1, required = true)
         public int ID;
         
     	 @Field(index=Index.YES)
         @ProtoField(number = 2)
         public String name;
         
         @ProtoField(number = 3)
         public String acronym;
         
         @ProtoField(number = 4)
         public String employeeNumber;

         public Organization() {}
         
         public Organization(int iD, String name, String acronym, String employeeNumber) {
			super();
			ID = iD;
			this.name = name;
			this.acronym = acronym;
			this.employeeNumber = employeeNumber;
		}

		public Organization(String name, String employeeNumber) {
             super();
             this.name = name;
             this.employeeNumber = employeeNumber;
         }

         /**
          * @return the ID
          */
         public int getID() {
             return ID;
         }
         /**
          * @param ID the ID to set
          */
         public void setID(int ID) {
             this.ID = ID;
         }
         /**
          * @return the name
          */
         public String getName() {
             return name;
         }
         /**
          * @param name the name to set
          */
         public void setName(String name) {
             this.name = name;
         }


         /**
          * @return the acronym
          */
         public String getAcronym() {
             return acronym;
         }
         /**
          * @param acronym the acronym to set
          */
         public void setAcronym(String acronym) {
             this.acronym = acronym;
         }
         /**
          * @return the acronym
          */
         public String getEmployeeNumber() {
             return employeeNumber;
         }
         /**
          * @param employeeNumber the employeeNumber to set
          */
         public void setEmployeeNumber(String employeeNumber) {
             this.employeeNumber = employeeNumber;
         }

         /* (non-Javadoc)
          * @see java.lang.Object#toString()
          */
         @Override
         public String toString() {
             return "Organization [name=" + name + ", acronym=" + acronym + " number of employees" + employeeNumber + "]";
         }
     }