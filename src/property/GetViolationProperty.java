package property;

public class GetViolationProperty extends ViolationProperty{

	private static GetSenderProperty singleton = new GetSenderProperty();
	private final String file = "ErrorCode_Violation";

	public static ViolationProperty getInstance() {
		return singleton;
	}

	public GetViolationProperty() {
		fileName = this.file;
	}

	@Override
	void next(ViolationPropertyContext context) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		context.changeState(GetSendeeProperty.getInstance());
	}

}
