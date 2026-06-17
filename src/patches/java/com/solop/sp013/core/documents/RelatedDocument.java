package com.solop.sp013.core.documents;

import com.solop.sp013.core.util.ElectronicInvoicingChanges;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Util;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Identity of a related (source) document, used by withholding/perception documents whose items are
 * the original invoices being withheld/perceived. Mirror of {@link ReversalDocument} but focused on
 * the source document identity (type / series-number / issue date) instead of the reversed amount.
 *
 * Built in the model-aware core layer (from the {@code WH_Withholding.SourceInvoice_ID}) and merely
 * consumed by the providers, keeping them decoupled from the Adempiere model.
 *
 * @author Gabriel Escalona
 */
public class RelatedDocument {

    /**	Default related document type (documento_relacionado_tipo): 01 = Factura	*/
    public static final String DEFAULT_DOCUMENT_TYPE = "01";

    /**	Source Document No (usually already SERIE-NUMERO once issued)	*/
    private String documentNo = null;
    /**	Source Fiscal Document No (SERIE-NUMERO) when it differs from the DocumentNo	*/
    private String fiscalDocumentNo = null;
    /**	Source Document Date	*/
    private Timestamp documentDate = null;
    /**	Related document type code (documento_relacionado_tipo)	*/
    private String documentType = DEFAULT_DOCUMENT_TYPE;
    /**	Source Currency Code (ISO)	*/
    private String currencyCode = null;
    /**	Withholding/Perception base amount of the source document	*/
    private BigDecimal baseAmount = null;

    public RelatedDocument() {

    }

    /**
     * Default constructor: convert a source invoice into its related-document identity.
     */
    public RelatedDocument(MInvoice sourceInvoice) {
        if(sourceInvoice == null) {
            throw new AdempiereException("@C_Invoice_ID@ @NotFound@");
        }
        convertDocument(sourceInvoice);
    }

    /**
     * Resolve the related document identity from the source invoice.
     */
    private void convertDocument(MInvoice sourceInvoice) {
        withDocumentNo(sourceInvoice.getDocumentNo());
        //	Fiscal number (SERIE-NUMERO) of the source document when captured (e.g. supplier invoice)
        String sourceFiscalDocumentNo = sourceInvoice.get_ValueAsString(ElectronicInvoicingChanges.SP013_FiscalDocumentNo);
        if(!Util.isEmpty(sourceFiscalDocumentNo, true)) {
            withFiscalDocumentNo(sourceFiscalDocumentNo);
        }
        withDocumentDate(sourceInvoice.getDateInvoiced());
        withCurrencyCode(MCurrency.getISO_Code(sourceInvoice.getCtx(), sourceInvoice.getC_Currency_ID()));
        MDocType sourceDocumentType = MDocType.get(sourceInvoice.getCtx(), sourceInvoice.getC_DocTypeTarget_ID());
        if(sourceDocumentType.get_ValueAsInt(ElectronicInvoicingChanges.SP013_TransactionType_ID) > 0) {
            MTable transactionTypeTable = MTable.get(sourceInvoice.getCtx(), ElectronicInvoicingChanges.SP013_TransactionType);
            if(transactionTypeTable != null) {
                PO transactionType = transactionTypeTable.getPO(sourceDocumentType.get_ValueAsInt(ElectronicInvoicingChanges.SP013_TransactionType_ID), sourceInvoice.get_TrxName());
                if(transactionType != null && !Util.isEmpty(transactionType.get_ValueAsString("Value"), true)) {
                    withDocumentType(transactionType.get_ValueAsString("Value"));
                }
            }
        }
    }

    public RelatedDocument withDocumentNo(String documentNo) {
        this.documentNo = documentNo;
        return this;
    }

    public RelatedDocument withFiscalDocumentNo(String fiscalDocumentNo) {
        this.fiscalDocumentNo = fiscalDocumentNo;
        return this;
    }

    public RelatedDocument withDocumentDate(Timestamp documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    public RelatedDocument withDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public RelatedDocument withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public RelatedDocument withBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
        return this;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public String getFiscalDocumentNo() {
        return fiscalDocumentNo;
    }

    public Timestamp getDocumentDate() {
        return documentDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }
}
