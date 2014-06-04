package org.kuali.kpme.edo.vote.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.vote.EdoVoteRecord;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;

public class EdoVoteRecordValidation {

    public static boolean validateVoteRecord(EdoVoteRecord voteRecord) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getDossierById(BigDecimal.valueOf(voteRecord.getDossierId()));

        if (StringUtils.equals(voteRecord.getVoteType(), EdoConstants.VOTE_TYPE_MULTIPLE)) {
            if (dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) ||
                dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCountTenure(), "yes");
                validateCount(voteRecord.getNoCountTenure(), "no");
                validateCount(voteRecord.getAbsentCountTenure(), "absent");
                validateCount(voteRecord.getAbstainCountTenure(), "abstain");
            }
            if (dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) ||
                dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCountPromotion(), "yes");
                validateCount(voteRecord.getNoCountPromotion(), "no");
                validateCount(voteRecord.getAbsentCountPromotion(), "absent");
                validateCount(voteRecord.getAbstainCountPromotion(), "abstain");
                validateAoeCode(voteRecord.getAoeCode());
            }
            // check for all zeroes
            validateTotalCount(voteRecord);

            return GlobalVariables.getMessageMap().getErrorCount() < 1;

        } else if (StringUtils.equals(voteRecord.getVoteType(), EdoConstants.VOTE_TYPE_SINGLE)) {

            if (dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) ||
                dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCountTenure(), voteRecord.getNoCountTenure());
            }
            if (dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) ||
                dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

                validateCount(voteRecord.getYesCountPromotion(), voteRecord.getNoCountPromotion());
            }

            validateAoeCode(voteRecord.getAoeCode());

            return GlobalVariables.getMessageMap().getErrorCount() < 1;
        }

        return false;
    }

    private static void validateTotalCount(EdoVoteRecord voteRecord) {
        EdoDossier dossier = EdoServiceLocator.getEdoDossierService().getDossierById(BigDecimal.valueOf(voteRecord.getDossierId()));

        if (dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE) ||
            dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {

            Integer total = voteRecord.getYesCountTenure() + voteRecord.getNoCountTenure() + voteRecord.getAbsentCountTenure() + voteRecord.getAbstainCountTenure();
            if (total < 1) {
                GlobalVariables.getMessageMap().putError(EdoConstants.ErrorKeys.ERROR_KEYS,"error.vote.multiple.zero");
            }
        }
        if (dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_PROMOTION) ||
                dossier.getDossierType().getDossierTypeName().equals(EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION)    ) {
            Integer total = voteRecord.getYesCountPromotion() + voteRecord.getNoCountPromotion() + voteRecord.getAbsentCountPromotion() + voteRecord.getAbstainCountPromotion();
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
