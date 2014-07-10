package org.kuali.kpme.edo.vote.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.vote.EdoVoteRecordBo;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;

public class EdoVoteRecordValidation {

    public static boolean validateVoteRecord(EdoVoteRecordBo voteRecord) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(voteRecord.getEdoDossierID());
        EdoDossierType edoDossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeById(dossier.getEdoDossierID());
        
        if (StringUtils.equals(voteRecord.getVoteType(), EdoConstants.VOTE_TYPE_MULTIPLE)) {
        	validateCount(voteRecord.getYesCount(), "yes");
            validateCount(voteRecord.getNoCount(), "no");
            validateCount(voteRecord.getAbsentCount(), "absent");
            validateCount(voteRecord.getAbstainCount(), "abstain");
            
            if (edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) ||
                	edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {
            	validateAoeCode(voteRecord.getAoeCode());
            }
        	/* original IU code
            if (edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) ||
            	edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCountTenure(), "yes");
                validateCount(voteRecord.getNoCountTenure(), "no");
                validateCount(voteRecord.getAbsentCountTenure(), "absent");
                validateCount(voteRecord.getAbstainCountTenure(), "abstain");
            }
            if (edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) ||
            	edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCountPromotion(), "yes");
                validateCount(voteRecord.getNoCountPromotion(), "no");
                validateCount(voteRecord.getAbsentCountPromotion(), "absent");
                validateCount(voteRecord.getAbstainCountPromotion(), "abstain");
                validateAoeCode(voteRecord.getAoeCode());
            }
            */
            // check for all zeroes
            validateTotalCount(voteRecord);

            return GlobalVariables.getMessageMap().getErrorCount() < 1;

        } else if (StringUtils.equals(voteRecord.getVoteType(), EdoConstants.VOTE_TYPE_SINGLE)) {
        	
            if (edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) ||
            	edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCount(), voteRecord.getNoCount());
            }
            if (edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) ||
            	edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCount(), voteRecord.getNoCount());
            }
			
            validateAoeCode(voteRecord.getAoeCode());

            return GlobalVariables.getMessageMap().getErrorCount() < 1;
        }

        return false;
    }

    private static void validateTotalCount(EdoVoteRecordBo voteRecord) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getEdoDossierById(voteRecord.getEdoDossierID());
        EdoDossierType edoDossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeById(dossier.getEdoDossierTypeID());
        
        if (edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) ||
        		edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

            Integer total = voteRecord.getYesCount() + voteRecord.getNoCount() + voteRecord.getAbsentCount() + voteRecord.getAbstainCount();
            if (total < 1) {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.multiple.zero");
            }
        }
        if (edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) ||
        		edoDossierType.getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {
            Integer total = voteRecord.getYesCount() + voteRecord.getNoCount() + voteRecord.getAbsentCount() + voteRecord.getAbstainCount();
            if (total < 1) {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.multiple.zero");
            }
        }
        return;
    }

    private static void validateCount(Integer yesCount, Integer noCount) {
        validateCount(yesCount, "yes");
        validateCount(noCount, "yes");
        if (GlobalVariables.getMessageMap().getErrorCount() < 1) {
            if (!(yesCount == 1 || yesCount == 0)) {
                    GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.single.invalid", "yes");
            }

            if (!(noCount == 1 || noCount == 0)) {
                    GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.single.invalid", "no");
            }

            if (GlobalVariables.getMessageMap().getErrorCount() < 1) {
                if (yesCount != null && noCount != null && yesCount.equals(0) && noCount.equals(0)) {
                    GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.count.required");
                } else if (yesCount.equals(1) && noCount.equals(1)) {
                    GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.count.pickone");
                }
            }
        }
    }

    private static void validateCount(Integer count, String label) {
        if (count == null) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.count.invalid", label);
        }
    }

    private static void validateAoeCode(String aoeCode) {
        if (StringUtils.isEmpty(aoeCode)) {
            GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.aoe.invalid");
        }
    }
}
