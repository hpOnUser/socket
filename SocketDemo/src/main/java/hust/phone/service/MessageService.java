package hust.phone.service;
import hust.phone.mapper.pojo.Messages;

public interface MessageService {
	public void updateMessage(Messages message);
	public Messages select(Messages messages);

}
