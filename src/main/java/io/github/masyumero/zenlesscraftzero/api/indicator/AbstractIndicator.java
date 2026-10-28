package io.github.masyumero.zenlesscraftzero.api.indicator;

public abstract class AbstractIndicator<I extends IIndicator.IEntry> implements IIndicator<I> {
    public AbstractIndicator() {
        registerIndicator();
    }

    protected abstract void add(I indicator);
}
