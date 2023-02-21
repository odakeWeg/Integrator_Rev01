package weg.net.tester.tag;

import weg.net.tester.utils.TagNameUtil;

//@Todo: Using LeafForTestTag for now
public class LeafMonitorRegisterCompareTag extends NodeCompareTag{
    //@Todo: Check how it is going to be implemented
    //@Todo: What should it extend?
    //@Todo: is the thread gonna be a TestTag?

	@Override
	protected void executeCommand() {
		createThread();
	}

	@Override
	public void setTagName() {
		this.tagName = TagNameUtil.REGISTER_MONITOR;
	}

    private void createThread() {
        //@Todo
    }

    private void startVariableMonitoring() {
        //@Todo
    }
}
