package org.kuali.kpme.edo.dossier.web;

import org.kuali.kpme.edo.base.web.EdoForm;

public class EdoDossierRouteForm extends EdoForm {

	private static final long serialVersionUID = 1885281174330633877L;
	 	private Integer cid;
	    private String candidateUsername;
	    private Integer dossierId;
	    private String dossierType;
        private String node;

		public Integer getCid() {
			return cid;
		}

		public void setCid(Integer cid) {
			this.cid = cid;
		}

		public String getCandidateUsername() {
			return candidateUsername;
		}

		public void setCandidateUsername(String candidateUsername) {
			this.candidateUsername = candidateUsername;
		}

		public Integer getDossierId() {
			return dossierId;
		}

		public void setDossierId(Integer dossierId) {
			this.dossierId = dossierId;
		}

		public String getDossierType() {
			return dossierType;
		}

		public void setDossierType(String dossierType) {
			this.dossierType = dossierType;
		}

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }
}
