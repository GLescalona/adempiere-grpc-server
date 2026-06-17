/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2020 E.R.P. Consultores y Asociados.                    *
 * All Rights Reserved.                                                       *
 * Contributor(s): Yamel Senih www.erpya.com                                  *
 *****************************************************************************/
package com.solop.sp013.core.documents;

import java.math.BigDecimal;

/**
 * Interface for fiscal document lines
 * @author Yamel Senih, yamel.senih@solopsoftware.com, Solop http://www.solopsoftware.com
 */
public interface IFiscalDocumentLine {
    
    String getdocumentLineUuid();
    String getProductValue();
    String getProductName();
    String getProductDescription();
    String getProductBarCode();
    String getLineDescription();
    String getProductUnitOfMeasure();
    BigDecimal getQuantity();
    BigDecimal getProductPriceList();
    BigDecimal getProductPrice();
    BigDecimal getTaxRate();
    String getTaxIndicator();
    BigDecimal getDiscount();
    BigDecimal getDiscountAmount();
    BigDecimal getLineNetAmount();
    BigDecimal getLineTotalAmount();
    String getWithholdingCode();
    BigDecimal getWithholdingRate();
    BigDecimal getWithholdingBaseAmount();
    RelatedDocument getRelatedDocument();
}