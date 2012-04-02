package net.triptech.metahive.web.model;

import net.triptech.metahive.model.DataType;
import net.triptech.metahive.model.Definition;

import org.apache.commons.lang.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;


/**
 * The Class RecordFilterVector.
 */
@RooJavaBean
public class RecordFilterVector {

	/** The definition. */
	private Definition definition;

	/** The criteria. */
	private String criteria;

	/** The constraint. */
	private String constraint;


	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public final String getCriteria() {

		String returnCriteria = "";

		if (StringUtils.isNotBlank(criteria)) {
			returnCriteria = criteria.trim();
		} else {
			if (StringUtils.isNotBlank(constraint)) {
				returnCriteria = constraint.trim();
			}
		}
		return returnCriteria;
	}

	/**
	 * Gets the constraint.
	 *
	 * @return the constraint
	 */
	public final String getConstraint() {

		String returnConstraint = "";

		if (StringUtils.isNotBlank(constraint) && StringUtils.isNotBlank(criteria)) {
			returnConstraint = constraint.trim();
		}
		return returnConstraint;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public final String getDescription() {
		StringBuilder sb = new StringBuilder();

		boolean percentage = false;

		if (definition != null && StringUtils.isNotBlank(definition.getName())) {

			if (definition.getDataType() == DataType.TYPE_PERCENTAGE) {
				percentage = true;
			}

			sb.append("'");
			sb.append(definition.getName());
			sb.append("'");

			if (StringUtils.isNotBlank(this.getConstraint())) {
				sb.append(" between '");
				sb.append(this.getCriteria());
				if (percentage) {
					sb.append("%");
				}
				sb.append("' and '");
				sb.append(this.getConstraint());
				if (percentage) {
					sb.append("%");
				}
				sb.append("'");
			} else {
				if (StringUtils.isNotBlank(this.getCriteria())) {
					sb.append(" equals '");
					sb.append(this.getCriteria());
					if (percentage) {
						sb.append("%");
					}
					sb.append("'");
				} else {
					sb.append(" is empty");
				}
			}
		}
		return sb.toString();
	}

}
