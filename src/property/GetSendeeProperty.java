package property;


public class GetSendeeProperty extends ViolationProperty{

	private static GetSendeeProperty singleton = new GetSendeeProperty();
	private final String file = "ErrorCode_Sendee";

	public static ViolationProperty getInstance() {
		return singleton;
	}

	public GetSendeeProperty() {
		fileName = this.file;
	}

	@Override
	void next(ViolationPropertyContext context) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		context.changeState(GetSenderProperty.getInstance());
	}

}
