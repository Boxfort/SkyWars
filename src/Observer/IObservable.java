package Observer;

public interface IObservable
{
	public void register(IObserver observer);
	public void unregister(IObserver observer);
	public void notifyObservers(String updateText);
}
