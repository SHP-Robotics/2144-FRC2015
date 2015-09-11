public class Talker {
	public Talker() {

	}

	public void runLesson(int i, boolean arg) {
		switch (i) {
		case 0:
			sayHello();
			break;
		case 1:
			sayHelloasVar();
			break;
		case 2:
			sayHello5Times();
			break;
		case 3:
			sayHello5TimesWithWhileLoop();
			break;
		case 4:
			sayHello5TimesWithForLoop();
			break;
		case 5:
			sayHelloifTrue(arg);
			break;
		default:
			sayHello();
		}
	}

	public void sayHello() {
		System.out.println("Hello, world!");
	}

	public double multiplyTwo(int x, int y) {
		return x * y;
	}

	public void sayHelloasVar() {
		String hello = "Hello, world!";
		System.out.println(hello);
	}

	public void sayHello5Times() {
		sayHello();
		sayHello();
		sayHello();
		sayHello();
		sayHello();
	}

	public void sayHello5TimesWithWhileLoop() {
		int i = 0;
		while (i < 5) {
			sayHello();
			i++;
		}
	}

	public void sayHello5TimesWithForLoop() {
		for (int i = 0; i < 5; i++) {
			sayHello();
		}
	}

	public void sayHelloifTrue(Boolean test) {
		if (test) {
			sayHello();
		} else {
			System.out.println("False!");
		}
	}
}
