import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 唐世杰
 * @since
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestLambda.class)
public class TestLambda {

	@Test
	public void testLambda() {
		final int num = 1;
		Converter<Integer, String> s = (param) ->
		{
			int re = param + num;
			System.out.println(re);
			return re;
		};
		s.convert(2);  // 输出结果为 3
	}

	public interface Converter<T1, T2> {
		int convert(int i);
	}
}
