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
		// TODO 自動生成されたメソッド・スタブ
		context.changeState(GetSendeeProperty.getInstance());
	}

}
