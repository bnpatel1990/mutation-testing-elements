import { PageObject } from './PageObject.po';

export class ProgressBar extends PageObject {
  private readonly progressBar = this.$('.progress-bar');

  public percentageText = async () => (await this.progressBar).getText();
  public barSize = async () => (await this.progressBar).getSize();
  public totalSize = () => this.host.getSize();
  public relativeBarWidth = async () => {
    const [totalSize, barSize] = await Promise.all([this.totalSize(), this.barSize()]);
    return Math.floor((barSize.width / totalSize.width) * 100);
  }
}
