package hust.phone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.phone.mapper.mapper.MessagesMapper;
import hust.phone.mapper.pojo.Messages;
import hust.phone.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired
	private MessagesMapper messagesMapper;

	//选择性更新
	@Override
	public void updateMessage(Messages message) {
		messagesMapper.updateByPrimaryKeySelective(message);
		
	}

	@Override
	public Messages select(Messages messages) {
		Messages selectByPrimaryKey = messagesMapper.selectByPrimaryKey(messages.getMessageid());
		return selectByPrimaryKey;
	}
	

	

}
