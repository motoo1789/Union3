package property;

public class GetSenderProperty extends ViolationProperty{

	private static GetSenderProperty singleton = new GetSenderProperty();
	private final String file = "ErrorCode_Sender";

	public static ViolationProperty getInstance() {
		return singleton;
	}

	public GetSenderProperty() {
		fileName = this.file;
	}

	@Override
	void next(ViolationPropertyContext context) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		context.changeState(GetViolationProperty.getInstance());
	}

}
