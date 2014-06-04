package org.kuali.kpme.edo.vote.web;

import org.kuali.kpme.edo.candidate.EdoSelectedCandidate;
import org.kuali.kpme.edo.item.web.EdoChecklistItemForm;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.vote.EdoVoteRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EdoVoteRecordForm extends EdoChecklistItemForm {

    private List<EdoVoteRecord> voteRecords = new ArrayList<EdoVoteRecord>();
    private EdoSelectedCandidate selectedCandidate;
    private EdoReviewLayerDefinition currentReviewLayerDefinition;
    private EdoReviewLayerDefinition principalReviewLayerDefinition;
    private EdoVoteRecord currentVoteRecord;
    private BigDecimal reviewLayerDefinitionId;
    private BigDecimal voteRecordId;
    private Integer yesCountTenure;
    private Integer noCountTenure;
    private Integer abstainCountTenure;
    private Integer absentCountTenure;
    private Integer yesCountPromotion;
    private Integer noCountPromotion;
    private Integer abstainCountPromotion;
    private Integer absentCountPromotion;
    private String aoeCode;
    private Integer voteRound;
    private Integer voteSubRound;
    private String voteRoundString;
    private Integer currentVoteRound;
    private Integer currentVoteSubRound;
    private Map<String, String> areaOfExcellenceChoices = EdoConstants.AREA_OF_EXCELLENCE;

    public List<EdoVoteRecord> getVoteRecords() {
        return voteRecords;
    }

    public void setVoteRecords(List<EdoVoteRecord> voteRecords) {
        this.voteRecords = voteRecords;
    }

    public EdoSelectedCandidate getSelectedCandidate() {
        return selectedCandidate;
    }

    public void setSelectedCandidate(EdoSelectedCandidate selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

    public EdoReviewLayerDefinition getCurrentReviewLayerDefinition() {
        return currentReviewLayerDefinition;
    }

    public void setCurrentReviewLayerDefinition(EdoReviewLayerDefinition reviewLayerDefinition) {
        this.currentReviewLayerDefinition = reviewLayerDefinition;
    }

    public EdoVoteRecord getCurrentVoteRecord() {
        return currentVoteRecord;
    }

    public void setCurrentVoteRecord(EdoVoteRecord voteRecord) {
        this.currentVoteRecord = voteRecord;
    }

    public BigDecimal getReviewLayerDefinitionId() {
        return reviewLayerDefinitionId;
    }

    public void setReviewLayerDefinitionId(BigDecimal reviewLayerDefinitionId) {
        this.reviewLayerDefinitionId = reviewLayerDefinitionId;
    }

    public BigDecimal getVoteRecordId() {
        return voteRecordId;
    }

    public void setVoteRecordId(BigDecimal voteRecordId) {
        this.voteRecordId = voteRecordId;
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

    public Integer getAbstainCountTenure() {
        return abstainCountTenure;
    }

    public void setAbstainCountTenure(Integer abstainCountTenure) {
        this.abstainCountTenure = abstainCountTenure;
    }

    public Integer getAbsentCountTenure() {
        return absentCountTenure;
    }

    public void setAbsentCountTenure(Integer absentCountTenure) {
        this.absentCountTenure = absentCountTenure;
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

    public Integer getAbstainCountPromotion() {
        return abstainCountPromotion;
    }

    public void setAbstainCountPromotion(Integer abstainCountPromotion) {
        this.abstainCountPromotion = abstainCountPromotion;
    }

    public Integer getAbsentCountPromotion() {
        return absentCountPromotion;
    }

    public void setAbsentCountPromotion(Integer absentCountPromotion) {
        this.absentCountPromotion = absentCountPromotion;
    }

    public String getAoeCode() {
        return aoeCode;
    }

    public void setAoeCode(String aoeCode) {
        this.aoeCode = aoeCode;
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

    public String getVoteRoundString() {
        return voteRoundString;
    }

    public void setVoteRoundString(String voteRoundString) {
        this.voteRoundString = voteRoundString;
    }

    public Map<String, String> getAreaOfExcellenceChoices() {
        return areaOfExcellenceChoices;
    }

    public EdoReviewLayerDefinition getPrincipalReviewLayerDefinition() {
        return principalReviewLayerDefinition;
    }

    public void setPrincipalReviewLayerDefinition(EdoReviewLayerDefinition principalReviewLayerDefinition) {
        this.principalReviewLayerDefinition = principalReviewLayerDefinition;
    }

    public Integer getCurrentVoteRound() {
        return currentVoteRound;
    }

    public void setCurrentVoteRound(Integer currentVoteRound) {
        this.currentVoteRound = currentVoteRound;
    }

    public Integer getCurrentVoteSubRound() {
        return currentVoteSubRound;
    }

    public void setCurrentVoteSubRound(Integer currentVoteSubRound) {
        this.currentVoteSubRound = currentVoteSubRound;
    }
}
