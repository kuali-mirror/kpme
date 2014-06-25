package org.kuali.kpme.edo.vote;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class EdoVoteRecord {
    private Integer voteRecordId;
    private Integer dossierId;
    private BigDecimal reviewLayerDefinitionId;
    private String voteType;
    private Integer yesCountTenure;
    private Integer noCountTenure;
    private Integer absentCountTenure;
    private Integer abstainCountTenure;
    private Integer yesCountPromotion;
    private Integer noCountPromotion;
    private Integer absentCountPromotion;
    private Integer abstainCountPromotion;
    private String aoeCode;
    private String aoeDescription;
    private String voteCategory;
    private Integer voteRound;
    private Integer voteSubRound;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;

    private EdoReviewLayerDefinition reviewLayerDefinition;

    public Integer getVoteRecordId() {
        return voteRecordId;
    }

    public void setVoteRecordId(Integer voteRecordId) {
        this.voteRecordId = voteRecordId;
    }

    public Integer getDossierId() {
        return dossierId;
    }

    public void setDossierId(Integer dossierId) {
        this.dossierId = dossierId;
    }

    public BigDecimal getReviewLayerDefinitionId() {
        return reviewLayerDefinitionId;
    }

    public void setReviewLayerDefinitionId(BigDecimal reviewLayerDefinitionId) {
        this.reviewLayerDefinitionId = reviewLayerDefinitionId;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public Integer getYesCountTenure() {
        return yesCountTenure;
    }

    public void setYesCountTenure(Integer yesCountTenure) {
        this.yesCountTenure = yesCountTenure;
    }

    public Integer getNoCountTenure() {
        return noCountTenure;
    }

    public void setNoCountTenure(Integer noCountTenure) {
        this.noCountTenure = noCountTenure;
    }

    public Integer getAbsentCountTenure() {
        return absentCountTenure;
    }

    public void setAbsentCountTenure(Integer absentCountTenure) {
        this.absentCountTenure = absentCountTenure;
    }

    public Integer getAbstainCountTenure() {
        return abstainCountTenure;
    }

    public void setAbstainCountTenure(Integer abstainCountTenure) {
        this.abstainCountTenure = abstainCountTenure;
    }

    public Integer getYesCountPromotion() {
        return yesCountPromotion;
    }

    public void setYesCountPromotion(Integer yesCountPromotion) {
        this.yesCountPromotion = yesCountPromotion;
    }

    public Integer getNoCountPromotion() {
        return noCountPromotion;
    }

    public void setNoCountPromotion(Integer noCountPromotion) {
        this.noCountPromotion = noCountPromotion;
    }

    public Integer getAbsentCountPromotion() {
        return absentCountPromotion;
    }

    public void setAbsentCountPromotion(Integer absentCountPromotion) {
        this.absentCountPromotion = absentCountPromotion;
    }

    public Integer getAbstainCountPromotion() {
        return abstainCountPromotion;
    }

    public void setAbstainCountPromotion(Integer abstainCountPromotion) {
        this.abstainCountPromotion = abstainCountPromotion;
    }

    public String getAoeCode() {
        return aoeCode;
    }

    public void setAoeCode(String aoeCode) {
        this.aoeCode = aoeCode;
    }

    public String getAoeDescription() {
        String aoeDescription = StringUtils.EMPTY;

        if (StringUtils.isNotEmpty(aoeCode)) {
            aoeDescription = EdoConstants.AREA_OF_EXCELLENCE.get(aoeCode);
        }

        return aoeDescription;
    }

    public String getVoteCategory() {
        return voteCategory;
    }

    public void setVoteCategory(String voteCategory) {
        this.voteCategory = voteCategory;
    }

    public Integer getVoteRound() {
        return voteRound;
    }

    public void setVoteRound(Integer voteRound) {
        this.voteRound = voteRound;
    }

    public Integer getVoteSubRound() {
        return voteSubRound;
    }

    public void setVoteSubRound(Integer voteSubRound) {
        this.voteSubRound = voteSubRound;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public EdoReviewLayerDefinition getReviewLayerDefinition() {
        if (ObjectUtils.isNull(reviewLayerDefinition) && reviewLayerDefinitionId != null) {
            String workflowId = EdoServiceLocator.getEdoDossierService().getEdoDossierById(this.dossierId.toString()).getWorkflowId();
            this.reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, reviewLayerDefinitionId);
        }
        return reviewLayerDefinition;
    }
}
