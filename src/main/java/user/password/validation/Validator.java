package user.password.validation;

public interface Validator<I> {
	boolean validate(I data);

}
